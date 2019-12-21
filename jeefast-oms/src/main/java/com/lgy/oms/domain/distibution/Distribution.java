package com.lgy.oms.domain.distibution;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import com.lgy.oms.enums.distibution.DistributionStatusEnum;
import com.lgy.oms.enums.distibution.DistributionLockWareHouseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Haru Skch
 * @since 2019-12-21
 */
@Data
@TableName("oms_distibution")
@EqualsAndHashCode(callSuper = false)
public class Distribution extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 配货状态
     * {@link DistributionStatusEnum}
     */
    private Integer flag;

    /**
     * 锁库
     * {@link DistributionLockWareHouseEnum}
     */
    private Integer lockWarehouse;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 来源单号
     */
    private String sourceId;

    /**
     * 店铺编码
     */
    private String shopCode;

    /**
     * 店铺名称
     */
    @TableField(exist = false)
    private String shopName;

    /**
     * 备注
     */
    private String remark;

}
