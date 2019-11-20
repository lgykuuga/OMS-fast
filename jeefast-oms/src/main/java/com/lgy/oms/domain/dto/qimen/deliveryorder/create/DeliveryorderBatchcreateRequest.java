package com.lgy.oms.domain.dto.qimen.deliveryorder.create;

import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 发货单批量创建
 *
 * @author LGy
 */

@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class DeliveryorderBatchcreateRequest {

    /**
     * 订单信息
     */
    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    private List<CreateOrder> orders;

    /**
     * 扩展属性
     */
    @XmlElement(name = "extendProps")
    private ExtendPropsEntity extendProps;


    public List<CreateOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CreateOrder> orders) {
        this.orders = orders;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}
