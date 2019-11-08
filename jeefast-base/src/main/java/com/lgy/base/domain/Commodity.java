package com.lgy.base.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品档案表 base_commodity
 *
 * @author lgy
 * @date 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_commodity")
public class Commodity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String gco;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String gna;

    /** 组合商品 */
    @Excel(name = "组合商品", readConverterExp = "0=是,1=否")
    private String combo;

    /** 缩略图url */
    private String imgUrl;

    /** 货主编码 */
    @Excel(name = "货主")
    private String owner;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

}
