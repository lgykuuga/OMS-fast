package com.lgy.oms.interfaces.qimen.bean.returnorder.create;

import com.lgy.oms.interfaces.qimen.bean.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * taobao.qimen.returnorder.create( 退货入库单创建接口 )
 *
 * @author LGy
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class ReturnOrderCreateRequest {

    private CreateReturnOrder returnOrder;

    /**
     * 明细
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<CreateReturnOrderLine> orderLines;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    public CreateReturnOrder getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(CreateReturnOrder returnOrder) {
        this.returnOrder = returnOrder;
    }

    public List<CreateReturnOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<CreateReturnOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}
