package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 转单策略店铺表 oms_strategy_convert
 *
 * @author lgy
 * @date 2019-10-31
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_convert_shop")
public class StrategyConvertShop extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 策略编码 */
    private String gco;

    /** 策略店铺编码*/
    private String shop;

    /** 策略店铺名称*/
    @Transient
    private String shopName;

    /** 开启自动(0:开启,1:关闭) */
    private String auto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGco() {
        return gco;
    }

    public void setGco(String gco) {
        this.gco = gco;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }
}
