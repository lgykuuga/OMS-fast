package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 下载订单表 oms_downloadorder
 *
 * @author lgy
 * @date 2019-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_downloadorder")
public class Downloadorder extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 店铺编码 */
    @Excel(name = "店铺编码")
    private String shop;

    /** 查单开始时间 */
    @Excel(name = "查单开始时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date bedt;

    /** 查单结束时间 */
    @Excel(name = "查单结束时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date endt;

    /** 执行时间 */
    @Excel(name = "执行时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date dodt;

    /** 每页数量 */
    @Excel(name = "每页数量")
    private Long size;

    /** 页数 */
    @Excel(name = "页数")
    private Long page;

    /** 请求返回消息 */
    @Excel(name = "请求返回消息")
    private String resp;

    /** 订单数量 */
    @Excel(name = "订单数量")
    private Integer onum;

    /** 请求状态 */
    @Excel(name = "请求状态", readConverterExp = "0=成功,1=失败")
    private String stat;

    /** 成功下载保存订单数量 */
    @Excel(name = "成功下载保存订单数量")
    private Integer snum;

    /** 成功下载更新订单数量 */
    @Excel(name = "成功下载更新订单数量")
    private Integer unum;

    /** 失败下载保存订单数量 */
    @Excel(name = "失败下载保存订单数量")
    private Integer fnum;

}
