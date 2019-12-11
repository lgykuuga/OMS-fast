package com.lgy.oms.service.business.impl;


import com.lgy.base.domain.Combo;
import com.lgy.base.domain.Commodity;
import com.lgy.base.service.IComboService;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.enums.order.OrderDetailTypeEnum;
import com.lgy.oms.service.business.IOrderDetailProcessingService;
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
}
