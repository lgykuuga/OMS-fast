package com.lgy.oms.domain.dto.qimen.entryorder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 收件人信息
 *
 * @author LGy
 */
@XStreamAlias("receiverInfo")
public class EntryReceiverInfo {

    /**
     * 公司名称
     */
    private String company;
    /**
     * 姓名  , 必填
     */
    private String name;

    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 固定电话
     */
    private String tel;
    /**
     * 移动电话  , 必填
     */
    private String mobile;
    /**
     * 收件人证件类型(1-身份证、2-军官证、3-护照、4-其他)
     */
    private String idType;
    /**
     * 收件人证件号码
     */
    private String idNumber;
    /**
     * email
     */
    private String email;
    /**
     * 国家二字码
     */
    private String countryCode;
    /**
     * 省份 , 必填
     */
    private String province;
    /**
     * 城市 , 必填
     */
    private String city;
    /**
     * 区域 , 必填
     */
    private String area;
    /**
     * 村镇
     */
    private String town;
    /**
     * 详细地址 , 必填
     */
    private String detailAddress;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
