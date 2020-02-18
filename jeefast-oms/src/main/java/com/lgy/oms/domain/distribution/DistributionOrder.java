package com.lgy.oms.domain.distribution;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import com.lgy.oms.domain.order.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 配货单信息表 oms_distribution_order
 *
 * @author lgy
 * @date 2020-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_distribution_order")
public class DistributionOrder extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 配货单号
     */
    @Excel(name = "配货单号")
    private String distributionId;

    /**
     * 订单单号
     */
    @Excel(name = "订单单号")
    private String orderId;

    /**
     * 来源单号
     */
    @Excel(name = "来源单号")
    private String sourceId;

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
     * 来源类型
     */
    @Excel(name = "来源类型")
    private Integer sourceType;

    /**
     * 尺码类型
     */
    @Excel(name = "尺码类型")
    private Integer sizeType;

    /**
     * sku种类数量
     */
    @Excel(name = "sku种类数量")
    private Integer skuNum;

    /**
     * 总件数
     */
    @Excel(name = "总件数")
    private Integer qty;

    /**
     * 商品编码集合
     */
    @Excel(name = "商品编码集合")
    private String commodity;

    /**
     * 总体积
     */
    @Excel(name = "总体积")
    private BigDecimal volume;

    /**
     * 总重量
     */
    @Excel(name = "总重量")
    private BigDecimal weight;

    /**
     * 运费
     */
    @Excel(name = "运费")
    private BigDecimal freightAmount;

    /**
     * 发货仓库编码
     */
    @Excel(name = "发货仓库编码")
    private String warehouse;

    /**
     * 物流商编码
     */
    @Excel(name = "物流商编码")
    private String logistics;

    /**
     * 快递单号
     */
    @Excel(name = "快递单号")
    private String expressNumber;

    /**
     * 推送时间
     */
    @Excel(name = "推送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /**
     * 发货时间
     */
    @Excel(name = "发货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendoutTime;

    /**
     * 订单买家信息
     */
    @TableField(exist = false)
    private OrderBuyerInfo orderBuyerinfo;

    /**
     * 订单支付信息
     */
    @TableField(exist = false)
    private OrderPayInfo orderPayinfo;

    /**
     * 订单业务类型信息
     */
    @TableField(exist = false)
    private OrderTypeInfo orderTypeinfo;

    /**
     * 订单状态类型信息
     */
    @TableField(exist = false)
    private OrderStatusInfo orderStatusinfo;

    /**
     * 订单拦截信息表
     */
    @TableField(exist = false)
    private OrderInterceptInfo orderInterceptInfo;

    /**
     * 订单明细信息
     */
    @TableField(exist = false)
    private List<DistributionDetail> distributionDetail;

}
