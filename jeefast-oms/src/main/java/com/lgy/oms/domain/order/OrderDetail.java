package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细信息表 oms_order_detail
 *
 * @author lgy
 * @date 2019-10-22
 */
@Data
@TableName("oms_order_detail")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 订单编号
     */
    @JsonProperty("订单编号")
    private String orderId;

    /**
     * 来源编号
     */
    @JsonProperty("来源编号")
    private String sourceId;

    /**
     * 发货仓库编码
     */
    @JsonProperty("发货仓库编码")
    private String warehouse;

    /**
     * 物流商编码
     */
    @JsonProperty("物流商编码")
    private String logistics;

    /**
     * 快递单号
     */
    @JsonProperty("快递单号")
    private String expressNumber;

    /**
     * 行序号
     */
    @JsonProperty("行序号")
    private String rowNumber;

    /**
     * 来源行序号
     */
    @JsonProperty("来源行序号")
    private String sourceRow;

    /**
     * 商品编码
     */
    @JsonProperty("商品编码")
    private String commodity;

    /**
     * 数量
     */
    @JsonProperty("数量")
    private Integer qty;

    /**
     * 下发配货单数量
     */
    @JsonProperty("下发配货单数量")
    private Integer distributionQty;

    /**
     * 发货完成数量
     */
    @JsonProperty("发货完成数量")
    private Integer sendQty;

    /**
     * 商品名称
     */
    @JsonProperty("商品名称")
    private String title;

    /**
     * 平台子订单编号
     */
    @JsonProperty("平台子订单编号")
    private String oid;

    /**
     * 退款状态
     */
    @JsonProperty("退款状态")
    private Integer refundStatus;

    /**
     * 商品类型
     */
    @JsonProperty("商品类型")
    private Integer type;

    /**
     * 商品图片绝对路径
     */
    @JsonProperty("商品图片绝对路径")
    private String picPath;

    /**
     * 商品数字ID
     */
    @JsonProperty("商品数字ID")
    private String numIid;

    /**
     * 商家外部编码
     */
    @JsonProperty("商家外部编码")
    private String outerIid;

    /**
     * 平台skuID
     */
    @JsonProperty("平台skuID")
    private String skuId;

    /**
     * 外部网店自己定义的Sku编号
     */
    @JsonProperty("外部sku")
    private String outerSkuId;

    /**
     * 销售单价
     */
    @JsonProperty("销售单价")
    private BigDecimal price;

    /**
     * 应付金额
     */
    @JsonProperty("应付金额")
    private BigDecimal totalFee;

    /**
     * 实付金额
     */
    @JsonProperty("实付金额")
    private BigDecimal payment;

    /**
     * 分摊之后的实付金额
     */
    @JsonProperty("分摊之后的实付金额")
    private BigDecimal divideOrderFee;

    /**
     * 尺寸
     */
    @JsonProperty("尺寸")
    private String size;

    /**
     * 商品条码
     */
    @JsonProperty("商品条码")
    private String barCode;

    /**
     * 品牌编码
     */
    @JsonProperty("品牌编号")
    private String brand;

    /**
     * 类别编码
     */
    @JsonProperty("类别编码")
    private String category;

    /**
     * 活动编码
     */
    @JsonProperty("活动编码")
    private String active;

    /**
     * 货主
     */
    @JsonProperty("货主")
    private String owner;

}
