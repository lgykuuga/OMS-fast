package com.lgy.oms.mq.audit;

import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.system.domain.SysUser;

/**
 * @Description 审核订单Message, 用于审单
 * @Author LGy
 * @Date 2020/1/8 16:13
 **/
public class AuditOrderMessage {

    /**
     * 订单信息
     */
    private OrderMain orderMain;

    /**
     * 其它参数
     */
    private AuditParamDTO param;

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

    public AuditParamDTO getParam() {
        return param;
    }

    public void setParam(AuditParamDTO param) {
        this.param = param;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
