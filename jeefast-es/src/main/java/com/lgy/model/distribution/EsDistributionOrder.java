package com.lgy.model.distribution;

import com.alibaba.fastjson.annotation.JSONField;
import com.lgy.es.annotation.EsField;
import com.lgy.es.common.EsModel;
import com.lgy.es.enums.EsFieldTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class EsDistributionOrder implements EsModel {

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="order_id")
    private Object orderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="order_type_id")
    private Object orderTypeId;

    @EsField(fieldType = EsFieldTypeEnum.TEXT)
    @JSONField(name="order_name")
    private Object orderName;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="sales_channel_enum_id")
    private Object salesChannelEnumId;

    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name="order_date")
    private Object orderDate;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="status_id")
    private Object statusId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="evaluate_status")
    private Object evaluateStatus;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="internal_code")
    private Object internalCode;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="is_viewed")
    private Object isViewed;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="source_id")
    private Object sourceId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="original_source")
    private Object originalSource;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="shopping_list_id")
    private Object shoppingListId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="invoice_type")
    private Object invoiceType;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="has_bill")
    private Object hasBill;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="transfer_warehouse_id")
    private Object transferWarehouseId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="contract_id")
    private Object contractId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="idx_order_id")
    private Object idxOrderId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="idx_inquiry_id")
    private Object idxInquiryId;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="created_by")
    private Object createdBy;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="car_vin")
    private Object carVin;

    @EsField(fieldType = EsFieldTypeEnum.KEYWORD)
    @JSONField(name="is_print")
    private Object isPrint;

    @EsField(fieldType = EsFieldTypeEnum.DATE)
    @JSONField(name="checkout_time")
    private Object checkoutTime;

    private List<EsDistributionDetail> orderItem;
}
