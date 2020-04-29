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
public class EsOrderType {

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD,isKey = true)
    @JSONField(name="order_id")
    private Object orderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="source_id")
    private Object sourceId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="aim_id")
    private Object aimId;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="source_type")
    private Object sourceType;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="delivery_type")
    private Object deliveryType;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="outbound_type")
    private Object outboundType;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="cod")
    private Object cod;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="invoice")
    private Object invoice;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="level")
    private Object level;

}
