package com.lgy.oms.domain.dto.qimen.deliveryorder.create;


import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 发货单创建
 *
 * @author LGy
 */

@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class DeliveryOrderCreateRequest {

    /**
     * 发货单信息
     */
    @XmlElement(name = "deliveryOrder")
    private CreateDeliveryOrder deliveryOrder;

    /**
     * 发货单信息
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<CreateDeliveryOrderLine> orderLines;

    /**
     * 扩展属性
     */
    @XmlElement(name = "extendProps")
    private ExtendPropsEntity extendProps;

    public DeliveryOrderCreateRequest() {
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }

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
