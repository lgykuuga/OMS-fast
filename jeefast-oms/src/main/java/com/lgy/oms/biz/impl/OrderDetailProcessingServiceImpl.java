package com.lgy.oms.biz.impl;


import com.lgy.base.domain.Combo;
import com.lgy.base.domain.Commodity;
import com.lgy.base.service.IComboService;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.ShopCommodity;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderDetailTypeEnum;
import com.lgy.oms.enums.order.OrderInterceptTypeEnum;
import com.lgy.oms.enums.strategy.ConvertMatchCommodityEnum;
import com.lgy.oms.service.IShopCommodityService;
import com.lgy.oms.biz.IOrderDetailProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单明细处理Service实现
 * @Author LGy
 * @Date 2019/12/2
 */
@Service
public class OrderDetailProcessingServiceImpl implements IOrderDetailProcessingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IComboService comboService;
    @Autowired
    ICommodityService commodityService;
    @Autowired
    IShopCommodityService shopCommodityService;

    @Override
    public CommonResponse<List<OrderDetail>> analysisCombCommodity(OrderDetail orderDetail) {
        //返回消息
        CommonResponse<List<OrderDetail>> rtnMessage = new CommonResponse<>();

        //放入组合商品解析出来的明细商品
        List<OrderDetail> combDetailList = new ArrayList<>();
        //解析成功标识
        boolean flag = true;

        try {
            if (StringUtils.isNotEmpty(orderDetail.getCommodity())) {
                //获取组合商品关系
                List<Combo> comboList = comboService.getListByParent(orderDetail.getCommodity());

                if (StringUtils.isNotEmpty(comboList)) {
                    int i = 1;
                    for (Combo combo : comboList) {
                        //订单组合商品明细
                        OrderDetail mxOrderDetail = new OrderDetail();
                        //订单组合商品明细赋值
                        BeanUtils.copyProperties(orderDetail, mxOrderDetail);
                        //组合明细商品档案
                        Commodity commodity = commodityService.getOne(combo.getChildren());
                        if (commodity == null) {
                            flag = false;
                            rtnMessage.setMsg("组合商品明细编码" + combo.getChildren() + "找不到对应商品档案信息");
                            break;
                        }
                        //组合明细商品编码
                        mxOrderDetail.setCommodity(commodity.getGco());
                        //组合明细商品名称
                        mxOrderDetail.setTitle(commodity.getGna());
                        //数量
                        mxOrderDetail.setQty(combo.getQty());
                        //类型:组合商品明细
                        mxOrderDetail.setType(OrderDetailTypeEnum.COMB_DETAIL.getCode());
                        //图片
                        mxOrderDetail.setPicPath(commodity.getImgUrl());
                        //尺寸
                        mxOrderDetail.setSize("");
                        //商品条码
                        mxOrderDetail.setBarCode("");
                        //品牌
                        mxOrderDetail.setBrand("");
                        //活动编码
                        mxOrderDetail.setActive("");
                        //行序号
                        if (StringUtils.isNotEmpty(orderDetail.getRowNumber())) {
                            //组合明细的来源行序号(SourceRow) = 父明细的行序号(RowNumber)
                            mxOrderDetail.setSourceRow(orderDetail.getRowNumber());
                            /**
                             * 组合明细的行序号(RowNumber) = 夫明细行序号 * (10000) + i
                             * - parent  1
                             *   - children 10001
                             *   - children 10002
                             * - parent  2
                             *   - children 20001
                             *   - children 20001
                             */
                            int prefix = Integer.parseInt(orderDetail.getRowNumber());
                            int rowNumber =  (prefix * 10000) + i;
                            mxOrderDetail.setRowNumber(rowNumber+"");
                        }

                        logger.info("解析组合商品[{}][{}]生成商品组合商品明细[{}][{}]",
                                orderDetail.getCommodity(), orderDetail.getQty(), mxOrderDetail.getCommodity(), orderDetail.getQty() * (mxOrderDetail.getQty()));

                        combDetailList.add(mxOrderDetail);
                        i++;
                    }
                } else {
                    flag = false;
                    rtnMessage.setMsg("组合商品" + orderDetail.getCommodity() + "无对应组合明细");
                }
            } else {
                flag = false;
                rtnMessage.setMsg("组合商品编码为空");
            }
        } catch (Exception e) {
            logger.error("解析组合商品出错:", e);
            flag = false;
            rtnMessage.setMsg("解析组合商品出错");
        }

        rtnMessage.setCode(flag ? Constants.SUCCESS : Constants.FAIL);
        rtnMessage.setData(combDetailList);
        return rtnMessage;
    }

    @Override
    public OrderMain matchCommodity(OrderMain orderMain, StrategyConvert strategy) {

        //匹配商品编码成功标识
        boolean flag = true;
        //记录匹配失败原因
        StringBuffer failReason = new StringBuffer();

        for (OrderDetail orderDetail : orderMain.getOrderDetails()) {

            if (StringUtils.isNotEmpty(orderDetail.getCommodity())) {

                if (ConvertMatchCommodityEnum.ORDER_OUT_NUM.getKey().equals(strategy.getMatchCommodity())) {
                    /** 根据订单外部编码匹配商品编码 */
                    if (StringUtils.isNotEmpty(orderDetail.getSkuId()) && StringUtils.isNotEmpty(orderDetail.getOuterSkuId())) {
                        //SKU外部编码
                        orderDetail.setCommodity(orderDetail.getOuterSkuId().trim());
                    } else if (StringUtils.isNotEmpty(orderDetail.getOuterIid()) && StringUtils.isEmpty(orderDetail.getSkuId())) {
                        //平台商品外部ID
                        orderDetail.setCommodity(orderDetail.getOuterIid().trim());
                    } else {
                        flag = false;
                        logger.error("订单[{}]根据外部编码匹配商品编码,行序号[{}]外部编码信息为空;",
                                orderMain.getOrderId(), orderDetail.getRowNumber());
                        failReason.append("根据外部编码匹配商品编码,行序号[").append(orderDetail.getRowNumber())
                                .append("]外部编码信息为空;");
                        break;
                    }
                } else if (ConvertMatchCommodityEnum.SHOP_COMMODITY.getKey().equals(strategy.getMatchCommodity())) {
                    /** 根据铺货关系匹配商品编码 */
                    if (StringUtils.isNotEmpty(orderDetail.getNumIid()) || StringUtils.isNotEmpty(orderDetail.getSkuId())
                            || StringUtils.isNotEmpty(orderDetail.getOuterIid()) || StringUtils.isNotEmpty(orderDetail.getOuterSkuId())) {
                        //匹配铺货关系
                        try {
                            CommonResponse<ShopCommodity> shopCommodityResp = shopCommodityService.getShopCommodityByOrder(orderMain.getShop(), orderDetail.getNumIid(),
                                    orderDetail.getSkuId(), orderDetail.getOuterIid(), orderDetail.getOuterSkuId());

                            //订单明细信息完整,并且能找到铺货关系
                            if (Constants.SUCCESS.equals(shopCommodityResp.getCode()) && shopCommodityResp.getData() != null) {
                                //匹配商品
                                orderDetail.setCommodity(shopCommodityResp.getData().getCommodity());
                            } else {
                                flag = false;
                                logger.error("订单[{}]存在商品没有维护铺货关系或多条相同铺货关系,匹配不到商品编码", orderDetail.getOrderId());
                                failReason.append("订单存在商品没有维护铺货关系或多条相同铺货关系,匹配不到商品编码;");
                                break;
                            }
                        } catch (Exception e) {
                            flag = false;
                            logger.error("订单" + orderDetail.getOrderId() + "请求解析铺货关系失败:", e);
                            failReason.append("订单请求解析铺货关系失败;");
                            break;
                        }
                    } else {
                        flag = false;
                        logger.error("订单[{}]商品平台商品ID或平台SKU编码为空,匹配不到商品编码", orderMain.getOrderId());
                        failReason.append("订单商品平台商品ID或平台SKU编码为空,匹配不到商品编码;");
                        break;
                    }
                }
            }
        }

        if (!flag) {
            //设置拦截
            orderMain.setIntercept(Constants.YES);
            //订单拦截信息
            OrderInterceptInfo orderIntercept = new OrderInterceptInfo();
            orderIntercept.setOrderId(orderMain.getOrderId());
            orderIntercept.setSourceId(orderMain.getSourceId());
            //设置匹配商品编码异常拦截
            orderIntercept.setType(OrderInterceptTypeEnum.MATCH_GOODS_CODE.getCode());
            //设置异常内容
            orderIntercept.setContent(failReason.toString());
            orderMain.setOrderInterceptInfo(orderIntercept);
        }

        return orderMain;
    }

    @Override
    public OrderMain analysisCombCommodity(OrderMain orderMain) {

        //解析组合商品成功标识
        boolean flag = true;
        //记录解析失败原因
        StringBuffer failReason = new StringBuffer();

        //订单明细信息
        if (StringUtils.isNotEmpty(orderMain.getOrderDetails())) {
            //新增的订单明细
            List<OrderDetail> addOrderDetails = new ArrayList<>();

            for (OrderDetail orderDetail : orderMain.getOrderDetails()) {

                if (StringUtils.isNotEmpty(orderDetail.getCommodity())) {
                    /** 存在商品编码,解析组合商品 */
                    //关联商品档案
                    Commodity commodity = commodityService.getOne(orderDetail.getCommodity());
                    if (commodity != null) {
                        //普通商品
                        orderDetail.setType(OrderDetailTypeEnum.DEFAULT.getCode());

                        //判断是否组合商品
                        if (Constants.YES.equals(commodity.getCombo())) {
                            //解析组合商品
                            CommonResponse<List<OrderDetail>> analysisResp = analysisCombCommodity(orderDetail);
                            if (Constants.SUCCESS.equals(analysisResp.getCode())) {
                                addOrderDetails.addAll(analysisResp.getData());
                                //组合商品
                                orderDetail.setType(OrderDetailTypeEnum.COMB.getCode());
                            } else {
                                flag =false;
                                failReason.append(analysisResp.getMsg());
                            }
                        }
                    } else {
                        //未解析商品
                        orderDetail.setType(OrderDetailTypeEnum.UNPARSED.getCode());
                    }
                }
            }

            if (flag) {
                //组合商品明细加入订单
                orderMain.getOrderDetails().addAll(addOrderDetails);
            } else {
                //设置订单拦截信息
                OrderInterceptInfo orderInterceptInfo = new OrderInterceptInfo();
                orderInterceptInfo.setOrderId(orderMain.getOrderId());
                orderInterceptInfo.setSourceId(orderMain.getSourceId());
                orderInterceptInfo.setType(OrderInterceptTypeEnum.ANALYSIS_COMBO_COMMODITY.getCode());
                orderInterceptInfo.setContent(failReason.toString());
                orderMain.setOrderInterceptInfo(orderInterceptInfo);
            }
        }

        return orderMain;
    }
}
