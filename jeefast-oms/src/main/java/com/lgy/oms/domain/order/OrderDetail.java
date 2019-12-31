package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private String orderId;

    /**
     * 来源编号
     */
    private String sourceId;

    /**
     * 发货仓库编码
     */
    private String warehouse;

    /**
     * 物流商编码
     */
    private String logistics;

    /**
     * 快递单号
     */
    private String expressNumber;

    /**
     * 行序号
     */
    private String rowNumber;

    /**
     * 来源行序号
     */
    private String sourceRow;

    /**
     * 商品编码
     */
    private String commodity;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 平台子订单编号
     */
    private String oid;

    /**
     * 退款状态
     */
    private Integer refundStatus;

    /**
     * 商品类型
     */
    private Integer type;

    /**
     * 商品图片绝对路径
     */
    private String picPath;

    /**
     * 商品数字ID
     */
    private String numIid;

    /**
     * 商家外部编码
     */
    private String outerIid;

    /**
     * 平台skuID
     */
    private String skuId;

    /**
     * 外部网店自己定义的Sku编号
     */
    private String outerSkuId;

    /**
     * 销售单价
     */
    private BigDecimal price;

    /**
     * 应付金额
     */
    private BigDecimal totalFee;

    /**
     * 实付金额
     */
    private BigDecimal payment;

    /**
     * 分摊之后的实付金额
     */
    private BigDecimal divideOrderFee;

    /**
     * 尺寸
     */
    private String size;

    /**
     * 商品条码
     */
    private String barCode;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 类别
     */
    private String category;

    /**
     * 活动编码
     */
    private String active;

}
