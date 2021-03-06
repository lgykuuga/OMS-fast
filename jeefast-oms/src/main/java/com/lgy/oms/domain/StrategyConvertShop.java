package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 转单策略店铺表 oms_strategy_convert
 *
 * @author lgy
 * @date 2019-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_strategy_convert_shop")
public class StrategyConvertShop extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 策略编码
     */
    private String gco;

    /**
     * 策略店铺编码
     */
    private String shop;

    /**
     * 策略店铺名称
     */
    @TableField(exist = false)
    private String shopName;

    /**
     * 开启自动(0:开启,1:关闭)
     */
    private String auto;

}
