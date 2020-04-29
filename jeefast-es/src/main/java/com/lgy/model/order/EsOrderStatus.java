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
public class EsOrderStatus {

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD,isKey = true)
    @JSONField(name="order_id")
    private Object orderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="source_id")
    private Object sourceId;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="flag")
    private Object flag;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="merger")
    private Object merger;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="split")
    private Object split;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="status")
    private Object status;
}
