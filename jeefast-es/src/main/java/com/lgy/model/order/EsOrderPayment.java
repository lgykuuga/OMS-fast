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
public class EsOrderPayment {

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD,isKey = true)
    @JSONField(name="order_id")
    private Object orderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="source_id")
    private Object sourceId;

    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name="order_time")
    private Object orderTime;

    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name="pay_time")
    private Object payTime;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="currency")
    private Object currency;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="order_amount")
    private Object orderAmount;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="pay_amount")
    private Object payAmount;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="received_amount")
    private Object receivedAmount;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="discount")
    private Object discount;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="tax_amount")
    private Object taxAmount;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="post")
    private Object post;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="freight_amount")
    private Object freightAmount;
}
