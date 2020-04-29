package com.lgy.model.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.lgy.es.annotation.EsField;
import com.lgy.es.enums.EsFieldTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author:D.J
 * @date: 2019-07-03
 **/
@Data
@Accessors(chain = true)
public class EsOrderDetail {

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD, isKey = true)
    @JSONField(name = "order_id")
    private Object orderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "source_id")
    private Object sourceId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "product_id")
    private Object productId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "warehouse")
    private Object warehouse;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "logistics")
    private Object logistics;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "express_number")
    private Object expressNumber;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "commodity")
    private Object commodity;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "qty")
    private Object qty;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "distributionQty")
    private Object distributionQty;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "send_qty")
    private Object sendQty;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "title")
    private Object title;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "oid")
    private Object oid;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "refund_status")
    private Object refundStatus;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "type")
    private Object type;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "num_iid")
    private Object numIid;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "outer_iid")
    private Object outerIid;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "sku_id")
    private Object skuId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "outer_sku_id")
    private Object outerSkuId;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "price")
    private Object price;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "total_fee")
    private Object totalFee;


    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "payment")
    private Object payment;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "divide_order_fee")
    private Object divideOrderFee;


    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name = "size")
    private Object size;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "bar_code")
    private Object barCode;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "brand")
    private Object brand;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "category")
    private Object category;


    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "active")
    private Object active;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "owner")
    private Object owner;

}
