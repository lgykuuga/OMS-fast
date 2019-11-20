package com.lgy.oms.domain.dto.qimen.deliveryorder.confirm;

import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 发货确认批量接口对象
 *
 * @author LGy
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class DeliveryOrderBatchConfirmRequest {

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    private List<ConfirmOrder> orders;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;


    public List<ConfirmOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ConfirmOrder> orders) {
        this.orders = orders;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}
