package com.lgy.oms.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 订单信息VO对象
 * @Author LGy
 * @Date 2020/1/16
 */
@Data
public class OrderVO {

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
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 买家ID
     */
    private String buyerId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 买家电话
     */
    private String buyerPhone;

    /**
     * 买家邮件地址
     */
    private String buyerEmail;

    /**
     * 买家身份证号
     */
    private String buyerCardId;

    /**
     * 收件人姓名
     */
    private String consigneeName;

    /**
     * 收件人移动电话
     */
    private String consigneeMobile;

    /**
     * 收件人邮箱地址
     */
    private String consigneeEmail;

    /**
     * 收件人身份证号
     */
    private String consigneeCardId;

    /**
     * 国家编码
     */
    private String nationCode;

    /**
     * 收件人国家
     */
    private String nation;

    /**
     * 省份编码
     */
    private String provinceCode;

    /**
     * 收件人省/州
     */
    private String province;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 收件人市
     */
    private String city;

    /**
     * 区域编码
     */
    private String districtCode;

    /**
     * 收件人区县
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 买家留言
     */
    private String buyerMessage;

    /**
     * 卖家留言
     */
    private String sellerMessage;

    /**
     * 订单拦截类型
     */
    private Integer type;

    /**
     * 拦截内容说明
     */
    private String content;

    /**
     * 下单时间
     */
    private String orderTime;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 币别
     */
    private String currency;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 实收金额
     */
    private BigDecimal receivedAmount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 邮费
     */
    private BigDecimal post;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 订单状态
     */
    private Integer flag;

    /**
     * 合并状态
     */
    private Integer merger;

    /**
     * 拆分状态
     */
    private Integer split;

    /**
     * 状态(有效、无效)
     */
    private Integer status;

    /**
     * 目标单号
     */
    private String aimId;

    /**
     * 来源类型
     */
    private Integer sourceType;

    /**
     * 发货类型(正常发货、第三方发货、刷单发货、刷单不发货)
     */
    private Integer deliveryType;

    /**
     * 出库类型(正常出库、补货出库、换货出库)
     */
    private Integer outboundType;

    /**
     * 货到付款
     */
    private Integer cod;

    /**
     * 发货级别(正常、加急、特急)
     */
    private Integer level;

    private String logisticsName;
    private String ownerName;
    private String platformName;
    private String shopName;
    private String warehouseName;

}
