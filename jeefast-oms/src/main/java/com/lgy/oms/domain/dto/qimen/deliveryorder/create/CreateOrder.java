package com.lgy.oms.domain.dto.qimen.deliveryorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 订单信息
 *
 * @author LGy
 */
@XStreamAlias("order")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateOrder {

    /**
     * 发货单信息
     */
    private CreateDeliveryOrder deliveryOrder;

    /**
     * 单据列表
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<CreateDeliveryOrderLine> orderLines;


    public CreateDeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(CreateDeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<CreateDeliveryOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<CreateDeliveryOrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
