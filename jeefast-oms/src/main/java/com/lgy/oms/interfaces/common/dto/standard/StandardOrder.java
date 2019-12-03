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
     * 卖家昵称
     */
    private String seller_nick;


    /**
     * 商品图片绝对途径
     */
    private String pic_path;

    /**
     * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String payment;

    /**
     * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String post_fee;

    /**
     * 收货人的姓名
     */
    private String receiver_name;

    /**
     * 收货人的所在省份
     */
    private String receiver_state;

    /**
     * 收货人的详细地址
     */
    private String receiver_address;

    /**
     * 收货人的邮编
     */
    private String receiver_zip;

    /**
     * 收货人的手机号码
     */
    private String receiver_mobile;

    /**
     * 商家的预计发货时间
     */
    private String est_con_time;

    /**
     * 收货人国籍
     */
    private String receiver_country;

    /**
     * 收货人街道地址
     */
    private String receiver_town;

    /**
     * 天猫国际官网直供主订单关税税费
     */
    private String order_tax_fee;

    /**
     * 交易状态。可选值:
     * TRADE_NO_CREATE_PAY(没有创建支付宝交易)
     * WAIT_BUYER_PAY(等待买家付款) *
     * SELLER_CONSIGNED_PART(卖家部分发货) *
     * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) *
     * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) *
     * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) *
     * TRADE_FINISHED(交易成功) *
     * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) *
     * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易) *
     * PAY_PENDING(国际信用卡支付付款确认中) *
     * WAIT_PRE_AUTH_CONFIRM(0元购合约中) *
     * PAID_FORBID_CONSIGN(拼团中订单或者发货强管控的订单，已付款但禁止发货)
     */
    private String status;

    /**
     * 商品价格。精确到2位小数；单位：元。如：200.07，表示：200元7分
     */
    private String price;

    /**
     * 可以使用trade.promotion_details查询系统优惠系统优惠金额（如打折，VIP，满就送等），
     * 精确到2位小数，单位：元。如：200.07，表示：200元7分
     */
    private String discount_fee;

    /**
     * 是否包含邮费。与available_confirm_fee同时使用。可选值:true(包含),false(不包含)
     */
    private String has_post_fee;

    /**
     * 商品金额（商品价格乘以数量的总金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String total_fee;

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
     * 买家昵称
     */
    private String buyer_nick;

    /**
     * 卖家手工调整金额，精确到2位小数，单位：元。如：200.07，表示：200元7分。
     * 来源于订单价格修改，如果有多笔子订单的时候，这个为0，单笔的话则跟[order].adjust_fee一样
     */
    private String adjust_fee;

    /**
     * 收货人的所在城市
     * 注：因为国家对于城市和地区的划分的有：省直辖市和省直辖县级行政区（区级别的）划分的，
     * 淘宝这边根据这个差异保存在不同字段里面比如：广东广州：
     * 广州属于一个直辖市是放在的receiver_city的字段里面；
     * 而河南济源：济源属于省直辖县级行政区划分，是区级别的，放在了receiver_district里面
     * 建议：程序依赖于城市字段做物流等判断的操作，
     * 最好加一个判断逻辑：如果返回值里面只有receiver_district参数，该参数作为城市
     */
    private String receiver_city;

    /**
     * 收货人的所在地区
     * 注：因为国家对于城市和地区的划分的有：省直辖市和省直辖县级行政区（区级别的）划分的，
     * 淘宝这边根据这个差异保存在不同字段里面比如：广东广州：
     * 广州属于一个直辖市是放在的receiver_city的字段里面；
     * 而河南济源：济源属于省直辖县级行政区划分，是区级别的，放在了receiver_district里面
     * 建议：程序依赖于城市字段做物流等判断的操作，
     * 最好加一个判断逻辑：如果返回值里面只有receiver_district参数，该参数作为城市
     */
    private String receiver_district;

    /**
     * 订单明细信息
     */
    private List<StandardOrderDetail> orderDetails;


}