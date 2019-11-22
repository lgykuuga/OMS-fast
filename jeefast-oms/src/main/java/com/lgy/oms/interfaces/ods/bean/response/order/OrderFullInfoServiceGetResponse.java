package com.lgy.oms.interfaces.ods.bean.response.order;

import com.lgy.oms.interfaces.ods.bean.response.BaseExecuteResponse;


/**
 * API接口响应实体
 *
 * @author admin
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
