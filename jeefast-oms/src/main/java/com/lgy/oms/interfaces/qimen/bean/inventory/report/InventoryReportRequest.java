package com.lgy.oms.interfaces.qimen.bean.inventory.report;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * taobao.qimen.inventory.report( 库存盘点通知接口 )
 * WMS调用奇门的接口,将库存盘点情况回传ERP
 *
 * @author LGy
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class InventoryReportRequest {

    /**
     * 总页数, int,必填
     */
    private int totalPage;

    /**
     * 当前页, 从1开始,int， 必填
     */
    private int currentPage;

    /**
     * 每页记录的条数, int,  必填
     */
    private int pageSize;

    /**
     * 仓库编码, string (50) ,  必填
     */
    private String warehouseCode;

    /**
     * 仓储系统的盘点单编码, string（50），条件必填
     */
    private String checkOrderId;

    /**
     * 盘点单编码, string（50），必填
     */
    private String checkOrderCode;

    /**
     * 货主编码, string (50) ,  必填
     */
    private String ownerCode;

    /**
     * 盘点时间，string(19)，string (19) , YYYY-MM-DD HH:MM:SS
     */
    private String checkTime;

    /**
     * 外部业务编码, 消息ID, 用于去重, ISV对于同一请求，分配一个唯一性的编码。
     * 用来保证因为网络等原因导致重复传输，请求不会被重复处理,  必填
     */
    private String outBizCode;

    /**
     * 备注, string (500)
     */
    private String remark;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<InventoryReportItem> items;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCheckOrderId() {
        return checkOrderId;
    }

    public void setCheckOrderId(String checkOrderId) {
        this.checkOrderId = checkOrderId;
    }

    public String getCheckOrderCode() {
        return checkOrderCode;
    }

    public void setCheckOrderCode(String checkOrderCode) {
        this.checkOrderCode = checkOrderCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public List<InventoryReportItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryReportItem> items) {
        this.items = items;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
