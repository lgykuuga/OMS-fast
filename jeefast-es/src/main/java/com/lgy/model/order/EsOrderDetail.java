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

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD,isKey = true)
    @JSONField(name="order_item_seq_id")
    private Object orderItemSeqId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="order_item_type_id")
    private Object orderItemTypeId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="product_id")
    private Object productId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="internal_num")
    private Object internalNum;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="parts_num_trim")
    private Object partsNumTrim;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="replace_parts_num")
    private Object replacePartsNum;

    @EsField(fieldType = EsFieldTypeEnum.TEXT)
    @JSONField(name="parts_name")
    private Object partsName;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="quantity")
    private Object quantity;

    @EsField(fieldType = EsFieldTypeEnum.TEXT)
    @JSONField(name="item_description")
    private Object itemDescription;

    @EsField(fieldType = EsFieldTypeEnum.NUM)
    @JSONField(name="original_seller_price")
    private Object originalSellerPrice;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="is_dispatch")
    private Object isDispatch;
}
