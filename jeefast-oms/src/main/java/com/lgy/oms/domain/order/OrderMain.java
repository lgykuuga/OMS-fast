package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单审核信息表 oms_order_audit
 *
 * @author lgy
 * @date 2019-10-22
 */
@ToString
@Data
@TableName("oms_order_main")
@EqualsAndHashCode(callSuper=false)
public class OrderMain extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 订单流水编号
     */
    private String orderId;

    /**
     * 来源单号
     */
    private String sourceId;

    /**
     * 店铺编码
     */
    private String shop;

    /**
     * 平台编码
     */
    private String platform;

    /**
     * 货主编码
     */
    private String owner;

    /**
     * 订单是否冻结
     */
    private String frozen;

    /**
     * 是否参与活动
     */
    private String active;

    /**
     * 是否人工编辑
     */
    private String handEdit;

    /**
     * 是否退款
     */
    private String refund;

    /**
     * 是否拦截
     */
    private String intercept;

    /**
     * 是否售后
     */
    private String afterSales;

    /**
     * 是否发票
     */
    private String invoice;

    /**
     * 是否用户锁定
     */
    private String orderLock;

    /**
     * 锁定人编码
     */
    private String lockUser;

    /**
     * 锁定时间
     */
    private String lockTime;

    /**
     * 订单标记
     */
    private String mark;

    /**
     * 标记内容
     */
    private String markContent;

    /**
     * 卖家备注旗帜
     */
    private String sellerFlag;

    /**
     * 尺码类型
     * @see com.lgy.oms.enums.order.OrderSizeTypeEnum
     */
    private Integer sizeType;

    /**
     * sku种类数量
     */
    private Integer skuNum;

    /**
     * 总件数
     */
    private Integer qty;

    /**
     * 商品编码集合
     */
    private String commodity;

    /**
     * 总体积
     */
    private BigDecimal volume;

    /**
     * 总重量
     */
    private BigDecimal weight;

    /**
     * 发货仓库编码(支持多条)
     */
    private String warehouse;

    /**
     * 物流商编码(支持多条)
     */
    private String logistics;

    /**
     * 快递单号(支持多条)
     */
    private String expressNumber;

    /**
     * 发货时间
     */
    private String sendoutTime;

    /**
     * 备注
     */
    private String remark;

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
    private List<OrderDetail> orderDetails;

}
