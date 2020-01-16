package com.lgy.oms.domain.distribution;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import com.lgy.oms.enums.distribution.DistributionInterceptEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Haru Skch
 * @since 2019-12-21
 */
@Data
@TableName("oms_distribution_intercept")
@EqualsAndHashCode(callSuper = false)
public class DistributionInterceptInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 配货状态
     * {@link DistributionInterceptEnum}
     */
    private Integer type;

    /**
     * 订单号
     */
    private String distributeId;

    /**
     * 拦截内容
     */
    private String content;

}
