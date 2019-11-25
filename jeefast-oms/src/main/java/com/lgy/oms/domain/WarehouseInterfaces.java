package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 仓库接口设置表 oms_warehouse_interfaces
 *
 * @author lgy
 * @date 2019-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_warehouse_interfaces")
public class WarehouseInterfaces extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouse;

    /** 是否加密(0：是  1：否) */
    @Excel(name = "是否加密(0：是  1：否)")
    private String encr;

    /** appkey */
    @Excel(name = "appkey")
    private String appk;

    /** 加密密串 */
    @Excel(name = "加密密串")
    private String secr;

    /** token */
    @Excel(name = "token")
    private String toke;

    /** 请求地址 */
    @Excel(name = "请求地址")
    private String surl;

    /** 数据格式(JSON/XML) */
    @Excel(name = "数据格式(JSON/XML)")
    private String fmat;

    /** 接口类型 */
    @Excel(name = "接口类型")
    private String jklx;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
