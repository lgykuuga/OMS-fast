package com.lgy.oms.domain.dto.qimen.stockout;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

@XStreamAlias("package")
public class StockoutPackage {
    /**
     * 物流公司编码, string (50) , SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通  、ZTO=中通 (ZTO) 、HTKY=百世汇通、UC=优速、STO=申通、TTKDEX=天天快递  、QFKD=全峰、FAST=快捷、
     * POSTB=邮政小包  、GTO=国通、YUNDA=韵达, OTHER=其他，必填,  (只传英文编码)
     */
    private String logisticsCode;
    /**
     * 物流公司名称
     */
    private String logisticsName;
    /**
     * 运单号
     */
    private String expressCode;
    /**
     * 包裹编号
     */
    private String packageCode;
    /**
     * 包裹长度 (厘米)
     */
    private Double length;
    /**
     * 包裹宽度 (厘米)
     */
    private Double width;
    /**
     * 包裹高度 (厘米)
     */
    private Double height;
    /**
     * 包裹重量 (千克)
     */
    private Double weight;
    /**
     * 包裹体积 (升, L)
     */
    private Double volume;
    /**
     * 发票号码
     */
    private String invoiceNo;
    /**
     * 包材信息
     */
    private List<StockoutPackageMaterial> packageMaterialList;
    /**
     * 商品信息
     */
    private List<StockoutItem> items;

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public List<StockoutPackageMaterial> getPackageMaterialList() {
        return packageMaterialList;
    }

    public void setPackageMaterialList(List<StockoutPackageMaterial> packageMaterialList) {
        this.packageMaterialList = packageMaterialList;
    }

    public List<StockoutItem> getItems() {
        return items;
    }

    public void setItems(List<StockoutItem> items) {
        this.items = items;
    }
}
