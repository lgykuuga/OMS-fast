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
public class EsOrderBuyer {

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD, isKey = true)
    @JSONField(name = "order_id")
    private Object orderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "source_id")
    private Object sourceId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "buyer_id")
    private Object buyerId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "buyer_name")
    private Object buyerName;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "buyer_phone")
    private Object buyerPhone;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "buyer_email")
    private Object buyerEmail;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "buyer_card_id")
    private Object buyerCardId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "consignee_name")
    private Object consigneeName;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "consignee_mobile")
    private Object consigneeMobile;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "consignee_email")
    private Object consigneeEmail;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "consignee_card_id")
    private Object consigneeCardId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "nation_code")
    private Object nationCode;


    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "nation")
    private Object nation;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "province_code")
    private Object provinceCode;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "province")
    private Object province;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "city_code")
    private Object cityCode;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "city")
    private Object city;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "district_code")
    private Object districtCode;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "district")
    private Object district;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "address")
    private Object address;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "zip_code")
    private Object zipCode;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "buyer_message")
    private Object buyerMessage;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name = "seller_message")
    private Object sellerMessage;
}
