package com.lgy.oms.domain.dto.qimen.deliveryorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 保险信息
 *
 * @author LGy
 */
@XStreamAlias("insurance")
public class CreateInsurance {

    /**
     * 保险类型
     */
    private String type;
    /**
     * 保险金额
     */
    private String amount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
