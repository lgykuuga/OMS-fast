package com.lgy.oms.domain.dto.ods.response.order;

import com.lgy.oms.domain.dto.ods.response.BaseExecuteResponse;


/**
 * API接口响应实体
 *
 * @author wonderful
 */
public class OrderFullInfoServiceGetResponse extends BaseExecuteResponse {

    private SaleOrderBean order;

    public SaleOrderBean getOrder() {
        return order;
    }

    public void setOrder(SaleOrderBean order) {
        this.order = order;
    }

}
