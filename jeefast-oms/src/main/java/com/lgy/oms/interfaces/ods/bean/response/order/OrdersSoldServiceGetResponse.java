package com.lgy.oms.interfaces.ods.bean.response.order;

import com.lgy.oms.interfaces.ods.bean.response.BaseExecuteResponse;

import java.util.List;


/**
 * API接口响应实体
 *
 * @author admin
 */
public class OrdersSoldServiceGetResponse extends BaseExecuteResponse {

    private int totalLines = 0;
    private List<SaleOrderBean> orders;

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public List<SaleOrderBean> getOrders() {
        return orders;
    }

    public void setOrders(List<SaleOrderBean> orders) {
        this.orders = orders;
    }

}
