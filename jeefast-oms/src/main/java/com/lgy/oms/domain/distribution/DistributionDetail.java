package com.lgy.oms.domain.distribution;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货单明细表 oms_distribution_detail
 *
 * @author lgy
 * @date 2020-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_distribution_detail")
public class DistributionDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 配货单号 */
    @Excel(name = "配货单号")
    private String distributionId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 来源编号 */
    @Excel(name = "来源编号")
    private String sourceId;

    /** 行序号 */
    @Excel(name = "行序号")
    private String rowNumber;

    /** 来源行序号 */
    @Excel(name = "来源行序号")
    private String sourceRow;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String commodity;

    /** 数量 */
    @Excel(name = "数量")
    private Long qty;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String title;

    /** 平台子订单编号 */
    @Excel(name = "平台子订单编号")
    private String oid;

    /** 退款状态 */
    @Excel(name = "退款状态")
    private String refundStatus;

    /** 商品类型 */
    @Excel(name = "商品类型")
    private Integer type;

    /** 商品图片绝对路径 */
    @Excel(name = "商品图片绝对路径")
    private String picPath;

    /** 商品数字ID */
    @Excel(name = "商品数字ID")
    private String numIid;

    /** 商家外部编码 */
    @Excel(name = "商家外部编码")
    private String outerIid;

    /** 平台skuID */
    @Excel(name = "平台skuID")
    private String skuId;

    /** 外部网店自己定义的Sku编号 */
    @Excel(name = "外部网店自己定义的Sku编号")
    private String outerSkuId;

    /** 销售单价 */
    @Excel(name = "销售单价")
    private Double price;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private Double totalFee;

    /** 实付金额 */
    @Excel(name = "实付金额")
    private Double payment;

    /** 分摊之后的实付金额 */
    @Excel(name = "分摊之后的实付金额")
    private Double divideOrderFee;

    /** 尺寸 */
    @Excel(name = "尺寸")
    private String size;

    /** 商品条码 */
    @Excel(name = "商品条码")
    private String barCode;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 类别 */
    @Excel(name = "类别")
    private String category;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    private String warehouse;

    /** 物流商 */
    @Excel(name = "物流商")
    private String logistics;

    /** 快递单号 */
    @Excel(name = "快递单号")
    private String expressNumber;

}
