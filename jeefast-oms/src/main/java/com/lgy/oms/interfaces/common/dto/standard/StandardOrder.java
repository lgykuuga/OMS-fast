package com.lgy.oms.interfaces.common.dto.standard;

import lombok.Data;

import java.util.List;

/**
 * @Description 对接各大平台转换成标准订单格式,
 * 对标淘宝订单详情API接口
 * @Author LGy
 * @Date 2019/12/2
 */
@Data
public class StandardOrder {

    /**
     * 交易编号 (父订单的交易编号)
     */
    private String tid;

    /**
     * 平台订单状态
     * @see com.lgy.oms.enums.order.PlatformOrderStatusEnum
     */
    private String status;


    /**
     * 卖家昵称
     */
    private String seller_nick;

    /**
     * 买家昵称
     */
    private String buyer_nick;

    /**
     * 买家姓名
     */
    private String buyer_name;

    /**
     * 买家邮件地址
     */
    private String buyer_email;

    /**
     * 买家电话
     */
    private String buyer_phone;

    /**
     * 买家身份证号
     */
    private String buyer_card_id;

    /**
     * 收货人的姓名
     */
    private String receiver_name;
    /**
     * 收货人的手机号码
     */
    private String receiver_mobile;

    /**
     * 收件人邮箱地址
     */
    private String receiver_email;

    /**
     * 收件人身份证号
     */
    private String receiver_card_id;

    /**
     * 收货人国籍
     */
    private String receiver_country;
    /**
     * 收货人的所在省份
     */
    private String receiver_state;
    /**
     * 收货人的所在城市
     */
    private String receiver_city;

    /**
     * 收货人的所在地区
     */
    private String receiver_district;

    /**
     * 收货人街道地址
     */
    private String receiver_town;

    /**
     * 收货人的详细地址
     */
    private String receiver_address;

    /**
     * 收货人的邮编
     */
    private String receiver_zip;
    /**
     * 买家留言
     */
    private String buyer_message;

    /**
     * 卖家备注（与淘宝网上订单的卖家备注对应，只有卖家才能查看该字段）
     */
    private String seller_memo;

    /**
     * 卖家备注旗帜（与淘宝网上订单的卖家备注旗帜对应，只有卖家才能查看该字段）
     * 红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
     */
    private String seller_flag;


    /**
     * 币别
     */
    private String currency;

    /**
     * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String payment;

    /**
     * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String post_fee;

    /**
     * 商品金额（商品价格乘以数量的总金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String total_fee;

    /**
     * 商品价格。精确到2位小数；单位：元。如：200.07，表示：200元7分
     * 商品价格 = 商品金额 - 税额
     * price = total_fee - order_tax_fee
     */
    private String price;

    /**
     * 关税税费
     */
    private String order_tax_fee;

    /**
     * 卖家手工调整金额，精确到2位小数，单位：元。如：200.07，表示：200元7分。
     * 来源于订单价格修改，如果有多笔子订单的时候，这个为0，单笔的话则跟[order].adjust_fee一样
     */
    private String adjust_fee;

    /**
     * 系统优惠金额（如打折，VIP，满就送等），
     * 精确到2位小数，单位：元。如：200.07，表示：200元7分
     */
    private String discount_fee;

    /**
     * 是否包含邮费。与available_confirm_fee同时使用。可选值:true(包含),false(不包含)
     */
    private String has_post_fee;



    /**
     * 交易创建时间。格式:yyyy-MM-dd HH:mm:ss
     */
    private String created;

    /**
     * 付款时间。格式:yyyy-MM-dd HH:mm:ss。订单的付款时间即为物流订单的创建时间。
     */
    private String pay_time;

    /**
     * 交易修改时间(用户对订单的任何修改都会更新此字段)。格式:yyyy-MM-dd HH:mm:ss
     */
    private String modified;

    /**
     * 交易结束时间。交易成功时间(更新交易状态为成功的同时更新)/确认收货时间或者交易关闭时间 。
     * 格式:yyyy-MM-dd HH:mm:ss
     */
    private String end_time;

    /**
     * 商家的预计发货时间
     */
    private String est_con_time;

    /**
     * 是否平台发货
     */
    private Boolean platform_send;

    /**
     * 是否货到付款
     */
    private Boolean cod;

    /**
     * 是否存在发票申请
     */
    private Boolean invoice;





    /**
     * 订单明细信息
     */
    private List<StandardOrderDetail> orderDetails;


    /**
     * 店铺编码
     */
    private String shop;

    /**
     * 平台编码
     */
    private String platform;

    /**
     * 货主
     */
    private String owner;

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
    private String express_number;

    /**
     * 是否存在退款
     */
    private Boolean exist_refund;


}