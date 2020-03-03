package com.lgy.oms.disruptor.tracelog;


import com.lgy.oms.domain.TraceLog;
import com.lgy.system.domain.SysUser;

/**
 * @Description 订单轨迹内容传递
 * @Author LGy
 * @Date 2019/12/27
 */
public class TraceLogEvent {

    /**
     * 订单流水编号
     */
    private String orderId;

    /**
     * 轨迹日志
     */
    private TraceLog traceLog;

    /**
     * 用户信息(用于线程切换)
     */
    private SysUser sysUser;

    public TraceLog getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(TraceLog traceLog) {
        this.traceLog = traceLog;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "TraceLogEvent{" +
                "orderId='" + orderId + '\'' +
                ", traceLog=" + traceLog +
                ", sysUser=" + sysUser +
                '}';
    }
}