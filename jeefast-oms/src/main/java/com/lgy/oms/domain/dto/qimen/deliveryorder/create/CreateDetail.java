package com.lgy.oms.domain.dto.qimen.deliveryorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 订单详情
 *
 * @author LGy
 */
@XStreamAlias("detail")
public class CreateDetail {

    private List<CreateItem> items;

    public List<CreateItem> getItems() {
        return items;
    }

    public void setItems(List<CreateItem> items) {
        this.items = items;
    }
}
