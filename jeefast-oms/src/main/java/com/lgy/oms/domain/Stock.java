package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 库存信息表 oms_stock
 *
 * @author lgy
 * @date 2019-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_stock")
public class Stock extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouse;

    /** 货主编码 */
    @Excel(name = "货主编码")
    private String owner;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String commodity;

    /** 数量 */
    @Excel(name = "数量")
    private Long qty;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
