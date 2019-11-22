package com.lgy.oms.interfaces.common.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 订单列表传输对象
 * @Author LGy
 * @Date 2019/10/14 16:49
 **/
@Data
@ToString
public class OrderDTO {

    /**
     * 来源单号
     */
    private String tid;
    /**
     * 平台状态
     */
    private Integer platformState;
    /**
     * 平台最后更新时间
     */
    private Date platformModified;
    /**
     * 店铺
     */
    private String shop;
    /**
     * 货主
     */
    private String owner;

    private Map<String, Object> params;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Integer getPlatformState() {
        return platformState;
    }

    public void setPlatformState(Integer platformState) {
        this.platformState = platformState;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Date getPlatformModified() {
        return platformModified;
    }

    public void setPlatformModified(Date platformModified) {
        this.platformModified = platformModified;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
