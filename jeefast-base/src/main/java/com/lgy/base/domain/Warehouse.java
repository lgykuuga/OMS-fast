package com.lgy.base.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 仓库信息表 base_warehouse
 *
 * @author lgy
 * @date 2019-10-09
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_warehouse")
public class Warehouse extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String gco;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String gna;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

}
