package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 订单买家信息表 oms_order_buyerinfo
 *
 * @author lgy
 * @date 2019-10-22
 */
@TableName("oms_order_buyerinfo")
public class OrderBuyerinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单编码
     */
    private String biid;

    /**
     * 来源单号
     */
    private String tid;

    /**
     * 买家ID
     */
    private String buyerId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 买家电话
     */
    private String buyerPhone;

    /**
     * 买家邮件地址
     */
    private String buyerEmail;

    /**
     * 收件人姓名
     */
    private String consigneeName;

    /**
     * 收件人移动电话
     */
    private String consigneeMobile;

    /**
     * 收件人固定电话
     */
    private String consigneeTelephone;

    /**
     * 收件人邮箱地址
     */
    private String consigneeEmail;

    /**
     * 国家编码
     */
    private String nationCode;

    /**
     * 收件人国家
     */
    private String nation;

    /**
     * 省份编码
     */
    private String provinceCode;

    /**
     * 收件人省/州
     */
    private String province;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 收件人市
     */
    private String city;

    /**
     * 区域编码
     */
    private String districtCode;

    /**
     * 收件人区县
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 买家留言
     */
    private String buyerMessage;

    /**
     * 卖家留言
     */
    private String sellerMessage;

    public String getBiid() {
        return biid;
    }

    public void setBiid(String biid) {
        this.biid = biid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigneeTelephone() {
        return consigneeTelephone;
    }

    public void setConsigneeTelephone(String consigneeTelephone) {
        this.consigneeTelephone = consigneeTelephone;
    }

    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    public void setConsigneeEmail(String consigneeEmail) {
        this.consigneeEmail = consigneeEmail;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getSellerMessage() {
        return sellerMessage;
    }

    public void setSellerMessage(String sellerMessage) {
        this.sellerMessage = sellerMessage;
    }
}
