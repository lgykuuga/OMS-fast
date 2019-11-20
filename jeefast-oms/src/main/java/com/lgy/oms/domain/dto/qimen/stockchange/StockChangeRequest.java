package com.lgy.oms.domain.dto.qimen.stockchange;

import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * taobao.qimen.stockchange.report( 库存异动通知接口 )
 *
 * WMS调用奇门的接口,将库存异动信息信息回传给ERP
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class StockChangeRequest {

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<StockChangeItem> items;

    @XmlElementWrapper(name = "batchs")
    @XmlElement(name = "batch")
    private List<StockChangeBatch> batchs;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;


    public List<StockChangeItem> getItems() {
        return items;
    }

    public void setItems(List<StockChangeItem> items) {
        this.items = items;
    }

    public List<StockChangeBatch> getBatchs() {
        return batchs;
    }

    public void setBatchs(List<StockChangeBatch> batchs) {
        this.batchs = batchs;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}
