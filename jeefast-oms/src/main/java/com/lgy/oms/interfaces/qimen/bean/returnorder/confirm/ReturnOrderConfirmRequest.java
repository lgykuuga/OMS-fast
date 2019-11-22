package com.lgy.oms.interfaces.qimen.bean.returnorder.confirm;

import com.lgy.oms.interfaces.qimen.bean.ExtendPropsEntity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * @Description taobao.qimen.returnorder.confirm(退货入库单确认接口)
 * @Author LGy
 * @Date 2019/11/20 17:41
 **/
public class ReturnOrderConfirmRequest {

    /**
     * 退货单信息
     */
    private ConfirmReturnOrder returnOrder;

    /**
     * 明细
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<ConfirmReturnOrderLine> orderLines;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    public ConfirmReturnOrder getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(ConfirmReturnOrder returnOrder) {
        this.returnOrder = returnOrder;
    }

    public List<ConfirmReturnOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<ConfirmReturnOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}
