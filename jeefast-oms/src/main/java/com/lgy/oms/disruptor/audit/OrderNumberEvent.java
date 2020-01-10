package com.lgy.oms.disruptor.audit;

import com.lgy.system.domain.SysUser;

/**
 * @Description 订单Event
 * @Author LGy
 * @Date 2020/1/7 15:13
 **/
public class OrderNumberEvent {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 用户信息(用于线程切换)
     */
    private SysUser sysUser;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
