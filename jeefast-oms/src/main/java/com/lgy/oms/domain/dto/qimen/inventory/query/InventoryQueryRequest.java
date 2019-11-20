package com.lgy.oms.domain.dto.qimen.inventory.query;

import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * taobao.qimen.inventory.query( 库存查询接口（多商品） )
 *
 * @author LGy.
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class InventoryQueryRequest {

    @XmlElementWrapper(name = "criteriaList")
    @XmlElement(name = "criteria")
    private List<InventoryQueryCriteria> criteriaList;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    /**
     * 备注
     */
    private String remark;

    public List<InventoryQueryCriteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<InventoryQueryCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }

}
