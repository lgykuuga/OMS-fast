package com.lgy.base.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 店铺档案表 base_shop
 *
 * @author lgy
 * @date 2019-10-10
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_shop")
public class Shop extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 店铺编码 */
    @Excel(name = "店铺编码")
    private String gco;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String gna;

    /** 平台编码 */
    @Excel(name = "平台")
    private String platform;

    /** 货主编码 */
    @Excel(name = "货主")
    private String owner;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
