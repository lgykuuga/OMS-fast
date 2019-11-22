package com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 发货确认：订单详情
 *
 * @author LGy
 */
@XStreamAlias("detail")
public class ConfirmDetail {

    private List<ConfirmItem> items;

    public List<ConfirmItem> getItems() {
        return items;
    }

    public void setItems(List<ConfirmItem> items) {
        this.items = items;
    }
}
