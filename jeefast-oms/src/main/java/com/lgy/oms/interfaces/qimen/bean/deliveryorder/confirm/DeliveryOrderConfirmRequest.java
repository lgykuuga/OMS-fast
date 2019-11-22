package com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm;

import com.lgy.oms.interfaces.qimen.bean.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 发货确认接口对象
 *
 * @author LGy
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class DeliveryOrderConfirmRequest {

    /**
     * 发货单信息
     */
    private ConfirmDeliveryOrder deliveryOrder;

    /**
     * 订单包裹信息
     */
    @XmlElementWrapper(name = "packages")
    @XmlElement(name = "package")
    private List<Package> packages;

    /**
     * 单据列表
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<ConfirmDeliveryOrderLine> orderLines;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;


    public ConfirmDeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(ConfirmDeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<ConfirmDeliveryOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<ConfirmDeliveryOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }

}
