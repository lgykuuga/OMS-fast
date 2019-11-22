package com.lgy.oms.interfaces.qimen.bean.deliveryorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 提货人信息
 *
 * @author LGy.
 */
@XStreamAlias("pickerInfo")
public class CreatePickerInfo {
    /**
     * 公司名称
     */
    private String company;
    /**
     * 姓名
     */
    private String name;
    /**
     * 固定电话
     */
    private String tel;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 证件号
     */
    private String id;
    /**
     * 车牌号
     */
    private String carNo;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
}
