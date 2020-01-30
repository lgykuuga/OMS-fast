package com.lgy.oms.biz.impl.trade;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.constant.ResponseCode;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.biz.*;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StandardOrderData;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.TradeParamDTO;
import com.lgy.oms.domain.order.*;
import com.lgy.oms.enums.order.*;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrderDetail;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IStrategyConvertService;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.ITradeStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 交易订单转换成正式订单实现
 * @Author LGy
 * @Date 2019/12/2
 */
@Service
public class TradeConvertServiceImpl implements ITradeConvertService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
     * 订单主服务
     */
    @Autowired
    IOrderMainService orderMainService;
    /**
     * 创建主订单服务
     */
    @Autowired
    ICreateOrderMainService createOrderMainService;
    /**
     * 订单快照
     */
    @Autowired
    ITradeStandardService tradeStandardService;
    /**
     * 订单明细处理
     */
    @Autowired
    IOrderDetailProcessingService orderDetailProcessingService;
    /**
     * 订单统计处理
     */
    @Autowired
    IOrderStatisticsService orderStatisticsService;

    /**
     * 事件驱动
     */
    @Autowired
    IEventDrivenService eventDrivenService;

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    @Override
    public CommonResponse<String> execute(String tid, TradeParamDTO param) {
        // 查询表信息
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        Trade trade = tradeService.getOne(queryWrapper);

        //失败原因
        StringBuilder failReason = new StringBuilder();

        if (trade == null) {
            failReason.append(tid).append("单据信息不完整;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        if (TradeTranformStatusEnum.WAIT_TRANFORM.getValue() != trade.getFlag()) {
            failReason.append(tid).append("已转单;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        //订单池存在单号
        List<String> orderList = orderMainService.getOrderIdBySourceId(tid, true);
        if (StringUtils.isNotEmpty(orderList)) {
            //订单池存在单号
            String orderIds = StringUtils.join(orderList, Constants.COMMA);
            failReason.append(tid).append("无需再次转单,已存在有效单据：").append(orderIds).append(Constants.SEMICOLON);
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        StrategyConvert strategy = strategyConvertService.getStrategyByShop(trade.getShop());
        if (strategy == null) {
            failReason.append(tid).append("转单策略不存在;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        //订单报文信息
        StandardOrderData latestStandardOrderData = tradeStandardService.getLatestStandardOrderData(tid);
        if (latestStandardOrderData == null) {
            failReason.append(tid).append("单据找不到快照信息,请检查代码;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        StandardOrder standardOrder = JSON.parseObject(latestStandardOrderData.getStandard(), StandardOrder.class);
        //转换成主订单信息
        OrderMain orderMain = convert(standardOrder, strategy, param);

        //匹配商品编码
        orderDetailProcessingService.matchCommodity(orderMain, strategy);
        //解析组合商品
        orderDetailProcessingService.analysisCombCommodity(orderMain);
        //订单统计处理
        orderStatisticsService.orderStatisticsMethod(orderMain);
        //保存订单
        createOrderMainService.saveOrder(orderMain);
        //保存轨迹服务
        traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                OrderOperateType.CONVERT.getValue(), TraceLevelType.STATUS.getKey(), "订单新增保存"));

        //更新订单状态
        CommonResponse<String> response = tradeService.updateTranformStatus(trade);
        if (ResponseCode.SUCCESS.equals(response.getCode())) {
            //完成转单流程,交由事件驱动判断下一步动作
            eventDrivenService.finishConvert(orderMain, strategy);
        } else {
            //保存更新订单状态异常信息
            traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                    OrderOperateType.CONVERT.getValue(), TraceLevelType.ABNORMAL.getKey(), response.getMsg()));
        }

        return new CommonResponse<String>().ok("转单完成");
    }

    /**
     * 对象转换
     *
     * @param standardOrder 标准订单
     * @param strategy      转单策略
     * @param param         其它参数
     * @return 主订单
     */
    private OrderMain convert(StandardOrder standardOrder, StrategyConvert strategy, TradeParamDTO param) {

        //自动触发转换
        boolean auto = param != null && param.getAuto();
        //生成退款明细
        boolean refund = param != null && param.getRefund();
        //是否存在退款明细
        boolean isExistRefund = false;

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
        orderMain.setActive(Constants.NO);
        //是否人工编辑
        orderMain.setHandEdit(Constants.NO);
        //是否拦截
        orderMain.setIntercept(Constants.NO);
        //是否售后
        orderMain.setAfterSales(Constants.NO);
        //是否已经开发票
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
        //尺码类型
        orderMain.setSizeType(0);
        //sku种类数量
        orderMain.setSkuNum(0);
        //总件数
        orderMain.setQty(0);
        //生成配货单数量
        orderMain.setDistributionQty(0);
        //商品编码集合
        orderMain.setCommodity("");
        //总体积
        orderMain.setVolume(BigDecimal.ZERO);
        //总重量
        orderMain.setVolume(BigDecimal.ZERO);
        //发货仓库编码
        orderMain.setWarehouse(standardOrder.getWarehouse());
        //物流商编码
        orderMain.setLogistics(standardOrder.getLogistics());
        //快递单号
        orderMain.setExpressNumber(standardOrder.getExpress_number());
        //发货时间
        orderMain.setSendoutTime(standardOrder.getEst_con_time());
        //备注
        orderMain.setRemark("");

        //订单状态信息
        OrderStatusInfo orderStatusInfo = new OrderStatusInfo();
        //订单流水编号
        orderStatusInfo.setOrderId("");
        //来源单号
        orderStatusInfo.setSourceId(standardOrder.getTid());
        //订单状态:新增
        orderStatusInfo.setFlag(OrderFlagEnum.NEW.getCode());
        //合并状态
        orderStatusInfo.setMerger(OrderMergeEnum.WAIT.getCode());
        //拆分状态
        orderStatusInfo.setSplit(OrderSplitEnum.WAIT.getCode());
        //订单状态:有效
        orderStatusInfo.setStatus(Constants.VALID);
        orderMain.setOrderStatusinfo(orderStatusInfo);

        //订单买家信息
        OrderBuyerInfo orderBuyer = new OrderBuyerInfo();
        //订单编码
        orderBuyer.setOrderId("");
        //来源单号
        orderBuyer.setSourceId(standardOrder.getTid());
        //买家ID
        orderBuyer.setBuyerId(standardOrder.getBuyer_nick());
        //买家姓名
        orderBuyer.setBuyerName(standardOrder.getBuyer_name());
        //买家电话
        orderBuyer.setBuyerPhone(standardOrder.getBuyer_phone());
        //买家邮件地址
        orderBuyer.setBuyerEmail(standardOrder.getBuyer_email());
        //买家身份证号
        orderBuyer.setBuyerCardId(standardOrder.getBuyer_card_id());
        //收件人姓名
        orderBuyer.setConsigneeName(standardOrder.getReceiver_name());
        //收件人移动电话
        orderBuyer.setConsigneeMobile(standardOrder.getReceiver_mobile());
        //收件人邮箱地址
        orderBuyer.setConsigneeEmail(standardOrder.getReceiver_email());
        //收件人身份证号
        orderBuyer.setConsigneeCardId(standardOrder.getReceiver_card_id());
        //收件人国家编码
        orderBuyer.setNationCode("");
        //收件人国家
        orderBuyer.setNation(standardOrder.getReceiver_country());
        //收件人省份编码
        orderBuyer.setProvinceCode("");
        //收件人省/州
        orderBuyer.setProvince(standardOrder.getReceiver_state());
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

        //订单支付信息
        OrderPayInfo orderPayinfo = new OrderPayInfo();
        //订单编码
        orderPayinfo.setOrderId("");
        //来源单号
        orderPayinfo.setSourceId(standardOrder.getTid());
        //下单时间
        orderPayinfo.setOrderTime(standardOrder.getCreated());
        //支付时间
        orderPayinfo.setPayTime(standardOrder.getPay_time());
        //币别
        orderPayinfo.setCurrency(standardOrder.getCurrency());
        //订单金额
        orderPayinfo.setOrderAmount(standardOrder.getTotal_fee() != null
                ? new BigDecimal(standardOrder.getTotal_fee()) : BigDecimal.ZERO);
        //支付金额
        orderPayinfo.setPayAmount(standardOrder.getPayment() != null
                ? new BigDecimal(standardOrder.getPayment()) : BigDecimal.ZERO);
        //实收金额
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

        //订单业务类型信息
        OrderTypeInfo orderTypeinfo = new OrderTypeInfo();
        //订单编码
        orderTypeinfo.setOrderId("");
        //来源单号
        orderTypeinfo.setSourceId(standardOrder.getTid());
        //目标单号
        orderTypeinfo.setAimId(standardOrder.getTid());
        //来源类型
        orderTypeinfo.setSourceType(OrderSourceTypeEnum.TRADE.getCode());
        if (standardOrder.getPlatform_send()) {
            //发货类型:平台发货
            orderTypeinfo.setDeliveryType(OrderSendOutTypeEnum.PLATFORM_DELIVER.getCode());
        } else {
            //发货类型:正常发货
            orderTypeinfo.setDeliveryType(OrderSendOutTypeEnum.NORMAL_DELIVER.getCode());
        }
        //出库类型:一般交易出库
        orderTypeinfo.setOutboundType(OrderOutBoundTypeEnum.JYCK.getCode());
        //货到付款
        orderTypeinfo.setCod(Integer.valueOf(standardOrder.getCod() ? Constants.YES : Constants.NO));
        //是否存在发票申请
        orderTypeinfo.setInvoice(Integer.valueOf(standardOrder.getInvoice() ? Constants.YES : Constants.NO));
        //发货级别:普通
        orderTypeinfo.setLevel(OrderSendOutLevelEnum.GENERAL.getCode());
        orderMain.setOrderTypeinfo(orderTypeinfo);

        //订单明细信息
        List<OrderDetail> orderDetails = new ArrayList<>(standardOrder.getOrderDetails().size());
        //行序号
        int rowNumber = 1;
        for (StandardOrderDetail standardDetail : standardOrder.getOrderDetails()) {

            //明细退款状态
            if (!OrderDetailRefundStatusEnum.NO_REFUND.name().equals(standardDetail.getRefund_status())) {
                isExistRefund = true;
                //是否生成退款订单
                if (!refund) {
                    continue;
                }
            }

            OrderDetail orderDetail = new OrderDetail();
            //订单编号
            orderDetail.setOrderId("");
            //来源编号
            orderDetail.setSourceId(standardOrder.getTid());
            //发货仓库编码
            orderDetail.setWarehouse(standardDetail.getStore_code());
            //物流商编码
            orderDetail.setLogistics(standardOrder.getLogistics());
            //快递单号
            orderDetail.setExpressNumber(standardDetail.getInvoice_no());
            //行序号
            orderDetail.setRowNumber(rowNumber + "");
            //来源行序号
            orderDetail.setSourceRow(rowNumber + "");
            //商品编码
            orderDetail.setCommodity("");
            //数量
            orderDetail.setQty(Integer.parseInt(standardDetail.getNum()));
            //商品名称
            orderDetail.setTitle(standardDetail.getTitle());
            //平台子订单编号
            orderDetail.setOid(standardDetail.getOid());
            //退款状态
            orderDetail.setRefundStatus(OrderDetailRefundStatusEnum.convert(standardDetail.getRefund_status()));
            //商品类型
            orderDetail.setType(OrderDetailTypeEnum.DEFAULT.getCode());
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
            rowNumber++;
        }

        //是否存在退款明细
        orderMain.setRefund(isExistRefund ? Constants.YES : Constants.NO);

        orderMain.setOrderDetails(orderDetails);

        if (auto) {
            orderMain.setCreateBy(Constants.SYSTEM);
        }

        return orderMain;

    }
}
