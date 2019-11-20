package com.lgy.oms.domain.dto.qimen.entryorder;

import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 入库单接口
 *
 * @author LGy.
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class EntryOrderRequest {

    private EntryOrder entryOrder;

    /**
     * 明细
     */
    @XmlElementWrapper(name = "orderLines")
    @XmlElement(name = "orderLine")
    private List<EntryOrderLine> orderLines;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    public EntryOrder getEntryOrder() {
        return entryOrder;
    }

    public void setEntryOrder(EntryOrder entryOrder) {
        this.entryOrder = entryOrder;
    }

    public List<EntryOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<EntryOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }

}
