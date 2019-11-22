package com.lgy.oms.interfaces.qimen.bean.stockout;

import com.lgy.oms.interfaces.qimen.bean.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * taobao.qimen.stockout.create( 出库单创建接口 )
 *
 * @author LGy
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class StockOutConfirmRequest {

    /**
     * 出库单信息
     */
    private StockoutDeliveryOrder deliveryOrder;

    /**
     * 出库单明细
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<StockoutOrderLine> orderLines;

    /**
     * 包裹信息
     */
    @XmlElementWrapper(name = "packages")
    @XmlElement(name = "package")
    private List<StockoutPackage> packages;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    public StockoutDeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(StockoutDeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<StockoutOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<StockoutOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public List<StockoutPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<StockoutPackage> packages) {
        this.packages = packages;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}
