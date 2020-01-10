package com.lgy.oms.disruptor.audit.sub;

import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.system.domain.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 审核订单Event
 * @Author LGy
 * @Date 2020/1/8 16:13
 **/
public class AuditOrderEvent {

    /**
     * 订单信息
     */
    private OrderMain orderMain;

    /**
     * 审核店铺策略
     */
    private StrategyAudit auditStrategy;

    /**
     * 其它参数
     */
    private AuditParamDTO param;

    /**
     * 拦截内容
     */
    private List<OrderInterceptInfo> orderInterceptInfoList = new ArrayList<>();

    /**
     * 用户信息(用于线程切换)
     */
    private SysUser sysUser;

    public OrderMain getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(OrderMain orderMain) {
        this.orderMain = orderMain;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public List<OrderInterceptInfo> getOrderInterceptInfoList() {
        return orderInterceptInfoList;
    }

    public StrategyAudit getAuditStrategy() {
        return auditStrategy;
    }

    public void setAuditStrategy(StrategyAudit auditStrategy) {
        this.auditStrategy = auditStrategy;
    }

    public AuditParamDTO getParam() {
        return param;
    }

    public void setParam(AuditParamDTO param) {
        this.param = param;
    }
}
