package com.lgy.base.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 组合商品表 base_combo
 *
 * @author lgy
 * @date 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_combo")
public class Combo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 组合商品编码 */
    @Excel(name = "组合商品编码")
    private String parent;

    /** 明细商品编码 */
    @Excel(name = "明细商品编码")
    private String children;

    /** 数量 */
    @Excel(name = "数量")
    private Long qty;

    /** 货主 */
    @Excel(name = "货主")
    private String owner;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
