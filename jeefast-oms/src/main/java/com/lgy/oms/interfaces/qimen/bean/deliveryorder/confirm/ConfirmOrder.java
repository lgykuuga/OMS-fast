package com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 发货确认接口
 *
 * @author LGy
 */
@XStreamAlias("order")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmOrder {

    /**
     * 发货单信息
     */
    private ConfirmDeliveryOrder deliveryOrder;
    /**
     * 订单包裹信息
     */
    @XmlElementWrapper(name = "packages")
    @XmlElement(name = "package")
    private List<ConfirmPackage> packages;
    /**
     * 单据列表
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<ConfirmDeliveryOrderLine> orderLines;

    public ConfirmDeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(ConfirmDeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<ConfirmPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<ConfirmPackage> packages) {
        this.packages = packages;
    }

    public List<ConfirmDeliveryOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<ConfirmDeliveryOrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
