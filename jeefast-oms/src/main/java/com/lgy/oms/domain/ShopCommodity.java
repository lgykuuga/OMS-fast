package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 铺货关系表 oms_shop_commodity
 *
 * @author lgy
 * @date 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_shop_commodity")
public class ShopCommodity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 店铺编码 */
    @Excel(name = "店铺编码")
    private String shop;

    /** 货主 */
    private String owner;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String commodity;

    /** 商品数字ID */
    @Excel(name = "商品数字ID")
    private String numIid;

    /** 商家外部编码 */
    @Excel(name = "商家外部编码")
    private String outerIid;

    /** 平台skuID */
    @Excel(name = "平台skuID")
    private String skuId;

    /** 外部Sku编号 */
    @Excel(name = "外部Sku编号")
    private String outerSkuId;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
