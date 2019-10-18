package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 库存锁定表 oms_stocklock
 *
 * @author lgy
 * @date 2019-10-18
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_stocklock")
public class Stocklock extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouse;

    /** 货主编码 */
    @Excel(name = "货主编码")
    private String owner;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String biid;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String commodity;

    /** 商品行序号 */
    private Long srid;

    /** 业务模块 */
    private String module;

    /** 数量 */
    private Long qty;

}
