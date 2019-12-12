package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单拦截信息表 oms_order_intercept
 *
 * @author lgy
 * @date 2019-12-12
 */
@Data
@TableName("oms_order_intercept")
public class OrderInterceptInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单流水编号
     */
    private String orderId;

    /**
     * 来源单号
     */
    private String sourceId;

    /**
     * 订单拦截类型
     */
    private Integer type;

    /**
     * 拦截内容说明
     */
    private String content;

}
