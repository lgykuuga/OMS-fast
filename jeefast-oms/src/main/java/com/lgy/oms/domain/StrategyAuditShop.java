package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 审单策略适用店铺表 oms_strategy_audit_shop
 *
 * @author lgy
 * @date 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_audit_shop")
public class StrategyAuditShop extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 策略编码
     */
    @Excel(name = "策略编码")
    private String gco;

    /**
     * 适用店铺
     */
    @Excel(name = "适用店铺")
    private String shop;

    /**
     * 策略店铺名称
     */
    @TableField(exist = false)
    private String shopName;

    /**
     * 开启自动(0:开启,1:关闭)
     */
    @Excel(name = "开启自动(0:开启,1:关闭)")
    private String auto;

}
