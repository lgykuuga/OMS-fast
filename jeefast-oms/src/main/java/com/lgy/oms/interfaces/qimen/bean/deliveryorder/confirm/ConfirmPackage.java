package com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 发货单确认包裹信息
 *
 * @author LGy
 */
@XStreamAlias("package")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmPackage {

    /**
     * 物流公司编码, string (50) , SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通  、ZTO=中通 (ZTO) 、
     * HTKY=百世汇通、UC=优速、STO=申通、TTKDEX=天天快递  、QFKD=全峰、FAST=快捷、POSTB=邮政小包  、GTO=国通、YUNDA=韵达, OTHER=其他，必填,  (只传英文编码)
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
     * 包裹长度(单位：厘米)
     */
    private String length;
    /**
     * 包裹宽度(单位：厘米)
     */
    private String width;
    /**
     * 包裹高度(单位：厘米)
     */
    private String height;
    /**
     * 包裹理论重量(单位：千克)
     */
    private String theoreticalWeight;
    /**
     * 包裹重量(单位：千克)
     */
    private String weight;
    /**
     * 包裹体积(单位：升)
     */
    private String volume;
    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 商品列表
     */
    @XmlElementWrapper(name = "packageMaterials")
    @XmlElement(name = "packageMaterial")
    private List<ConfirmPackageMaterial> packageMaterialList;

    /**
     * 商品列表
     */
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<ConfirmItem> items;

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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTheoreticalWeight() {
        return theoreticalWeight;
    }

    public void setTheoreticalWeight(String theoreticalWeight) {
        this.theoreticalWeight = theoreticalWeight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public List<ConfirmPackageMaterial> getPackageMaterialList() {
        return packageMaterialList;
    }

    public void setPackageMaterialList(List<ConfirmPackageMaterial> packageMaterialList) {
        this.packageMaterialList = packageMaterialList;
    }

    public List<ConfirmItem> getItems() {
        return items;
    }

    public void setItems(List<ConfirmItem> items) {
        this.items = items;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }


}
