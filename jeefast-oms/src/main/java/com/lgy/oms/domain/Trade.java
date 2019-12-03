package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易订单表 oms_trade
 *
 * @author lgy
 * @date 2019-10-15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_trade")
public class Trade extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 平台交易单号 */
    @Excel(name = "平台交易单号")
    private String tid;

    /** 平台交易状态 */
    @Excel(name = "平台交易状态")
    private Integer status;

    /** 交易修改时间 */
    @Excel(name = "交易修改时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date modified;

    /** 数据校验字段 */
    @Excel(name = "数据校验字段")
    private String hashcode;

    /** 请求返回消息 */
    @Excel(name = "请求返回消息")
    private String response;

    /** 标准订单数据 */
    @Excel(name = "标准订单数据")
    private String standard;

    /** 店铺编码 */
    @Excel(name = "店铺编码")
    private String shop;

    /** 货主编码 */
    @Excel(name = "货主编码")
    private String owner;

    /** 转单状态 */
    @Excel(name = "转单状态")
    private Integer flag;

    /** 更新次数 */
    @Excel(name = "更新次数")
    private Integer frequency;

}
