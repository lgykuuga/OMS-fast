package com.lgy.oms.service.business.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.StandardOrderData;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.order.*;
import com.lgy.oms.enums.TradeTranformStatusEnum;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrderDetail;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IStrategyConvertService;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.business.ITradeConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 交易订单转换成正式订单实现
 * @Author LGy
 * @Date 2019/12/2
 */
@Service
public class TradeConvertServiceImpl implements ITradeConvertService {

    /**
     * 交易订单
     */
    @Autowired
    ITradeService tradeService;
    /**
     * 转单策略
     */
    @Autowired
    IStrategyConvertService strategyConvertService;
    /**
     * 审核订单
     */
    @Autowired
    IOrderMainService orderMainService;

    @Override
    public CommonResponse<String> execute(String tid, Map<String, Object> map) {
        // 查询表信息
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        Trade trade = tradeService.getOne(queryWrapper);

        if (trade == null) {
            return new CommonResponse<String>().error(Constants.FAIL, tid + "信息不完整;");
        }

        if (!TradeTranformStatusEnum.WAIT_TRANFORM.equals(trade.getFlag())) {
            return new CommonResponse<String>().error(Constants.FAIL, tid + "已转单或已取消;");
        }

        StrategyConvert strategy = strategyConvertService.getStrategyByShop(trade.getShop());
        if (strategy == null) {
            return new CommonResponse<String>().error(Constants.FAIL, tid + "单据店铺没有维护转单策略");
        }

        //订单报文信息
        StandardOrderData standard = trade.getStandard();
        StandardOrder standardOrder = JSON.parseObject(standard.getStandard(), StandardOrder.class);
        //转换成主订单信息
        OrderMain orderMain = convert(standardOrder, strategy, map);

        return null;

    }

    /**
     * 对象转换
     *
     * @param standardOrder 标准订单
     * @param strategy      转单策略
     * @param map           其它参数
     * @return 主订单
     */
    private OrderMain convert(StandardOrder standardOrder, StrategyConvert strategy, Map<String, Object> map) {

        //自动触发转换
        boolean auto = map != null && map.get("auto") != null && (boolean) map.get("auto");
        //生成退款明细
        boolean refund = map != null && map.get("refund") != null && (boolean) map.get("refund");

        /** 订单主信息 */
        OrderMain orderMain = new OrderMain();
        //订单流水编号,调用保存时再生成
        orderMain.setOrderId("");
        //来源单号
        orderMain.setSourceId(standardOrder.getTid());
        //店铺编码
        orderMain.setShop(standardOrder.getShop());
        //平台编码
        orderMain.setPlatform(standardOrder.getPlatform());
        //货主编码
        orderMain.setOwner(standardOrder.getOwner());
        //订单是否冻结
        orderMain.setFrozen(Constants.NO);
        //是否参与活动
        orderMain.setFrozen(Constants.NO);
        //是否人工编辑
        orderMain.setHandEdit(Constants.NO);

        //是否拦截
        orderMain.setIntercept(Constants.NO);
        //是否售后
        orderMain.setAftersales(Constants.NO);
        //是否发票
        orderMain.setInvoice(Constants.NO);
        //是否用户锁定
        orderMain.setOrderLock(Constants.NO);
        //锁定人编码
        orderMain.setLockUser("");
        //锁定时间
        orderMain.setLockTime("");
        //订单标记
        orderMain.setMark("");
        //标记内容
        orderMain.setMarkContent("");
        //卖家备注旗帜
        orderMain.setSellerFlag(standardOrder.getSeller_flag());

        //是否存在退款明细
        orderMain.setRefund("");
        //尺码类型
        orderMain.setSizeType(0);
        //sku种类数量
        orderMain.setSkuNum(0);
        //总件数
        orderMain.setQty(0);
        //商品编码集合
        orderMain.setCommodity("");
        //总体积
        orderMain.setVolume(BigDecimal.ZERO);
        //总重量
        orderMain.setVolume(BigDecimal.ZERO);
        //发货仓库编码
        orderMain.setWarehouse("");
        //物流商编码
        orderMain.setLogistics("");
        //快递单号
        orderMain.setExpressNumber("");
        //发货时间
        orderMain.setSendoutTime("");
        //备注
        orderMain.setRemark("");

        /** 订单买家信息 */
        OrderBuyerinfo orderBuyer = new OrderBuyerinfo();
        //订单编码
        orderBuyer.setOrderId("");
        //来源单号
        orderBuyer.setSourceId(standardOrder.getTid());
        //买家ID
        orderBuyer.setBuyerId(standardOrder.getBuyer_nick());
        //买家姓名
        orderBuyer.setBuyerName("");
        //买家电话
        orderBuyer.setBuyerPhone("");
        //买家邮件地址
        orderBuyer.setBuyerEmail("");
        //收件人姓名
        orderBuyer.setConsigneeName(standardOrder.getReceiver_name());
        //收件人移动电话
        orderBuyer.setConsigneeMobile(standardOrder.getReceiver_mobile());
        //收件人固定电话
        orderBuyer.setConsigneeTelephone("");
        //收件人邮箱地址
        orderBuyer.setConsigneeEmail("");
        //收件人国家编码
        orderBuyer.setNationCode("");
        //收件人国家
        orderBuyer.setNation(standardOrder.getReceiver_country());
        //收件人省份编码
        orderBuyer.setProvinceCode("");
        //收件人省/州
        orderBuyer.setProvince("");
        //收件人城市编码
        orderBuyer.setCityCode("");
        //收件人市
        orderBuyer.setCity(standardOrder.getReceiver_city());
        //收件人区编码
        orderBuyer.setDistrictCode("");
        //收件人区
        orderBuyer.setDistrict(standardOrder.getReceiver_district());
        //详细地址
        orderBuyer.setAddress(standardOrder.getReceiver_address());
        //邮政编码
        orderBuyer.setZipCode(standardOrder.getReceiver_zip());
        //买家留言
        orderBuyer.setBuyerMessage(standardOrder.getBuyer_message());
        //卖家留言
        orderBuyer.setSellerMessage(standardOrder.getSeller_memo());
        orderMain.setOrderBuyerinfo(orderBuyer);

        /** 订单支付信息 */
        OrderPayinfo orderPayinfo = new OrderPayinfo();
        //订单编码
        orderPayinfo.setOrderId("");
        //来源单号
        orderPayinfo.setSourceId(standardOrder.getTid());
        //下单时间
        orderPayinfo.setOrderTime(standardOrder.getCreated());
        //支付时间
        orderPayinfo.setPayTime(standardOrder.getPay_time());
        //币别
        orderPayinfo.setCurrency("");
        //订单金额
        orderPayinfo.setOrderAmount(standardOrder.getPrice() != null
                ? new BigDecimal(standardOrder.getPrice()) : BigDecimal.ZERO);
        //支付金额
        orderPayinfo.setPayAmount(standardOrder.getPayment() != null
                ? new BigDecimal(standardOrder.getPayment()) : BigDecimal.ZERO);
        //TODO 实收金额
        orderPayinfo.setReceivedAmount(standardOrder.getPayment() != null
                ? new BigDecimal(standardOrder.getPayment()) : BigDecimal.ZERO);
        //折扣
        orderPayinfo.setDiscount(standardOrder.getDiscount_fee() != null
                ? new BigDecimal(standardOrder.getDiscount_fee()) : BigDecimal.ZERO);
        //税额
        orderPayinfo.setTaxAmount(standardOrder.getOrder_tax_fee() != null
                ? new BigDecimal(standardOrder.getOrder_tax_fee()) : BigDecimal.ZERO);
        //邮费
        orderPayinfo.setPost(standardOrder.getPost_fee() != null
                ? new BigDecimal(standardOrder.getPost_fee()) : BigDecimal.ZERO);
        //运费
        orderPayinfo.setFreightAmount(BigDecimal.ZERO);
        orderMain.setOrderPayinfo(orderPayinfo);

        /** 订单业务类型信息 */
        OrderTypeinfo orderTypeinfo = new OrderTypeinfo();
        //订单编码
        orderTypeinfo.setOrderId("");
        //来源单号
        orderTypeinfo.setSourceId(standardOrder.getTid());
        //目标单号
        orderTypeinfo.setAimId(standardOrder.getTid());
        //来源类型
        orderTypeinfo.setSourceType(0);
        //单据类型(线上订单、线下订单、特殊订单)
        orderTypeinfo.setOrderType(0);
        //发货类型(正常发货、第三方发货、刷单发货、刷单不发货)
        orderTypeinfo.setDeliveryType(0);
        //出库类型(正常出库、补货出库、换货出库)
        orderTypeinfo.setOutboundType(0);
        //货到付款
        orderTypeinfo.setCod(0);
        //发票申请
        orderTypeinfo.setInvoice(0);
        //发货级别(正常、加急、特急)
        orderTypeinfo.setLevel(0);
        orderMain.setOrderTypeinfo(orderTypeinfo);

        /** 订单明细信息 */
        List<OrderDetail> orderDetails = new ArrayList<>(standardOrder.getOrderDetails().size());
        //行序号
        int rowNumber = 0;
        for (StandardOrderDetail standardDetail : standardOrder.getOrderDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            //订单编号
            orderDetail.setOrderId("");
            //来源编号
            orderDetail.setSourceId(standardOrder.getTid());
            //发货仓库编码
            orderDetail.setWarehouse(standardDetail.getStore_code());
            //物流商编码
            orderDetail.setLogistics("");
            //快递单号
            orderDetail.setExpressNumber(standardDetail.getInvoice_no());
            //行序号
            orderDetail.setRowNumber(rowNumber+"");
            //来源行序号
            orderDetail.setSourceRow(rowNumber+"");
            //TODO 商品编码
            orderDetail.setCommodity("");
            //数量
            orderDetail.setQty(Integer.parseInt(standardDetail.getNum()));
            //商品名称
            orderDetail.setTitle(standardDetail.getTitle());
            //平台子订单编号
            orderDetail.setOid(standardDetail.getOid());
            //退款状态
            orderDetail.setRefundStatus(standardDetail.getRefund_status());
            //TODO 商品类型
            orderDetail.setType(0);
            //商品图片绝对路径
            orderDetail.setPicPath(standardDetail.getPic_path());
            //商品数字ID
            orderDetail.setNumIid(standardDetail.getNum_iid());
            //商家外部编码
            orderDetail.setOuterIid(standardDetail.getOuter_iid());
            //平台skuID
            orderDetail.setSkuId(standardDetail.getSku_id());
            //外部网店自己定义的Sku编号
            orderDetail.setOuterSkuId(standardDetail.getOuter_sku_id());
            //销售单价
            orderDetail.setPrice(standardDetail.getPrice() != null
                    ? new BigDecimal(standardDetail.getPrice()) : BigDecimal.ZERO);
            //应付金额
            orderDetail.setTotalFee(standardDetail.getTotal_fee() != null
                    ? new BigDecimal(standardDetail.getTotal_fee()) : BigDecimal.ZERO);
            //实付金额
            orderDetail.setPayment(standardDetail.getTotal_fee() != null
                    ? new BigDecimal(standardDetail.getTotal_fee()) : BigDecimal.ZERO);
            //分摊之后的实付金额
            orderDetail.setDivideOrderFee(standardDetail.getDivide_order_fee() != null
                    ? new BigDecimal(standardDetail.getDivide_order_fee()) : BigDecimal.ZERO);
            //尺寸
            orderDetail.setSize("");
            //商品条码
            orderDetail.setBarCode("");
            //品牌
            orderDetail.setBrand("");
            //活动编码
            orderDetail.setActive("");
            orderDetails.add(orderDetail);
        }

        orderMain.setOrderDetails(orderDetails);

        return orderMain;

    }
}
