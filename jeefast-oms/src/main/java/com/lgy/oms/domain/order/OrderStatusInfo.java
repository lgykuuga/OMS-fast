package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 订单状态信息表 oms_order_status
 *
 * @author lgy
 * @date 2019-10-22
 */
@Data
@TableName("oms_order_status")
@EqualsAndHashCode(callSuper=false)
public class OrderStatusInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 订单编码
     */
    private String orderId;

    /**
     * 来源单号
     */
    private String sourceId;

    /**
     * 订单状态
     */
    private Integer flag;

    /**
     * 合并状态
     */
    private Integer merger;

    /**
     * 拆分状态
     */
    private Integer split;

    /**
     * 状态(有效、无效)
     */
    private Integer status;

}
