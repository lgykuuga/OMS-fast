package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺接口设置表 oms_shop_interfaces
 *
 * @author lgy
 * @date 2019-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_shop_interfaces")
public class ShopInterfaces extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 店铺编码
     */
    @Excel(name = "店铺编码")
    private String shop;

    /**
     * 平台编码
     */
    @Excel(name = "平台编码")
    private String platform;

    /**
     * 货主编码
     */
    @Excel(name = "货主编码")
    private String owner;

    /**
     * appkey
     */
    @Excel(name = "appkey")
    private String appk;

    /**
     * sercret
     */
    @Excel(name = "sercret")
    private String secr;

    /**
     * token
     */
    @Excel(name = "token")
    private String toke;

    /**
     * 请求地址
     */
    @Excel(name = "请求地址")
    private String surl;

    /**
     * 接口类型
     */
    @Excel(name = "接口类型")
    private String jklx;

    /**
     * 是否自动下载订单
     */
    @Excel(name = "是否自动下载订单", readConverterExp = "0=正常,1=停用")
    private String zdxz;

    /**
     * 下载订单策略
     */
    @Excel(name = "下载订单策略")
    private String xdcl;

    /**
     * 上次下载订单结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上次下载订单结束时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date scjs;

    /**
     * 延迟抓单分钟数
     */
    @Excel(name = "延迟抓单分钟数")
    private String ycfz;

    /**
     * 下单前置分钟数
     */
    @Excel(name = "下单前置分钟数")
    private Integer xdqz;

    /**
     * 下单后置分钟数
     */
    @Excel(name = "下单后置分钟数")
    private Integer xdhz;

    /**
     * 补偿抓单是否开启
     */
    @Excel(name = "补偿抓单是否开启", readConverterExp = "0=正常,1=停用")
    private String bczd;

    /**
     * 补偿抓单下载距今多少分钟前的订单
     */
    @Excel(name = "补偿抓单下载距今多少分钟前的订单")
    private Long bcmi;

    /**
     * 是否自动回传发货状态
     */
    @Excel(name = "自动回传发货状态", readConverterExp = "0=正常,1=停用")
    private String fhhc;

    /**
     * 是否回传订单明细
     */
    @Excel(name = "回传订单明细", readConverterExp = "0=正常,1=停用")
    private String odmx;

    /**
     * 是否自动上传库存
     */
    @Excel(name = "自动上传库存", readConverterExp = "0=正常,1=停用")
    private String sckc;

    /**
     * 是否自动下载退货退款通知
     */
    @Excel(name = "自动下载退货退款通知", readConverterExp = "0=正常,1=停用")
    private String tktz;

    /**
     * 上次下载退款通知结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上次下载退款通知结束时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date sctk;

    /**
     * 下载通知前置分钟数
     */
    @Excel(name = "下载通知前置分钟数")
    private Integer tzqz;

    /**
     * 下载通知后置分钟数
     */
    @Excel(name = "下载通知后置分钟数")
    private Integer tzhz;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

}
