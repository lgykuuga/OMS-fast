package com.lgy.model.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.lgy.es.annotation.EsField;
import com.lgy.es.common.EsModel;
import com.lgy.es.enums.EsFieldTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @author:D.J
 * @date: 2019-07-02
 * @doc:这个类在通过EsSearchEngine.buildSimpleQuery方法构造查询器的时候结合EsParam类使用， EsOrderHeader param = new EsOrderHeader();
 * 首先 EsOrderHeader param = new EsOrderHeader();
 * 例1：单个查询：查询订单状态为"ORDER_WAIT_PAYED"的订单
 * param.setStatus(new EsQueryParam(EsQueryOperateEnum.MUST,"ORDER_WAIT_PAYED"));
 * 或(简写)
 * param.setStatus("ORDER_WAIT_PAYED"); //不用 EsQueryParam 时默认MUST操作
 * 例2：集合查询：查询订单状态为"ORDER_WAIT_PAYED"和"ORDER_CANCELLED"的订单
 * param.setStatus(new EsQueryParam(EsQueryOperateEnum.MUST,{"ORDER_WAIT_PAYED","ORDER_CANCELLED"}));
 * 或(简写)
 * param.setStatus({"ORDER_WAIT_PAYED","ORDER_CANCELLED"}) //这里相当于数据库中的in查询
 * 例3：多重条件查询：查询订单状态为"ORDER_WAIT_PAYED"且不为"ORDER_CANCELLED"的订单
 * EsQueryParam esParam1 = new EsQueryParam(EsQueryOperateEnum.MUST,ORDER_WAIT_PAYED);
 * EsQueryParam esParam2 = new EsQueryParam(EsQueryOperateEnum.MUST_NOT,ORDER_CANCELLED);
 **/
@Data
@Accessors(chain = true)
public class EsOrderMain implements EsModel {

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "order_id")
    private Object orderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "source_id")
    private Object sourceId;

    @EsField(fieldType = EsFieldTypeEnum.OBJECT)
    @JSONField(name = "shop")
    private Object shop;

    @EsField(fieldType = EsFieldTypeEnum.OBJECT)
    @JSONField(name = "platform")
    private Object platform;

    @EsField(fieldType = EsFieldTypeEnum.OBJECT)
    @JSONField(name = "owner")
    private Object owner;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "frozen")
    private Object frozen;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "active")
    private Object active;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "hand_edit")
    private Object handEdit;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "refund")
    private Object refund;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "intercept")
    private Object intercept;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "after_sales")
    private Object afterSales;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "invoice")
    private Object invoice;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "order_lock")
    private Object orderLock;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "lock_user")
    private Object lockUser;

    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name = "lock_time")
    private Object lockTime;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "mark")
    private Object mark;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "seller_flag")
    private Object sellerFlag;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "size_type")
    private Object sizeType;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "sku_num")
    private Object skuNum;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "qty")
    private Object qty;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "distribution_order_qty")
    private Object distributionOrderQty;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "distribution_qty")
    private Object distributionQty;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "send_qty")
    private Object sendQty;

    /**
     * 总体积
     */
    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "volume")
    private Object volume;

    /**
     * 总重量
     */
    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "weight")
    private Object weight;

    /**
     * 快递单号(支持多条)
     */
    @EsField(fieldType = EsFieldTypeEnum.TEXT)
    @JSONField(name = "express_number")
    private String expressNumber;
    /**
     * 发货时间
     */
    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name = "sendout_time")
    private String sendoutTime;
    /**
     * 缺货标识
     */
    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "lack_stock")
    private String lackStock;
    /**
     * 库存占用标识
     */
    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "lock_stock")
    private String lockStock;

    /**
     * 创建者
     */
    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "create_by")
    private Object createBy;

    /**
     * 创建时间
     */
    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name = "create_time")
    private Object createTime;

    /**
     * 更新者
     */
    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "update_by")
    private Object updateBy;

    /**
     * 更新时间
     */
    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name = "update_time")
    private Object updateTime;

    @EsField(fieldType = EsFieldTypeEnum.OBJECT)
    @JSONField(name = "order_payment")
    private EsOrderPayment orderPayment;

    @EsField(fieldType = EsFieldTypeEnum.OBJECT)
    @JSONField(name = "order_status")
    private EsOrderStatus orderStatus;

    @EsField(fieldType = EsFieldTypeEnum.OBJECT)
    @JSONField(name = "order_buyer")
    private EsOrderBuyer orderBuyer;

    @EsField(fieldType = EsFieldTypeEnum.OBJECT)
    @JSONField(name = "order_type")
    private EsOrderType orderType;

    @EsField(fieldType = EsFieldTypeEnum.NESTED)
    @JSONField(name = "order_detail")
    private List<EsOrderDetail> orderDetails;
}
