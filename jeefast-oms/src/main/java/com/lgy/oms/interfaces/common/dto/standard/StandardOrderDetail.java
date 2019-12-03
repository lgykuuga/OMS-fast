package com.lgy.oms.interfaces.common.dto.standard;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 对接各大平台转换成标准订单明细格式
 * @Author LGy
 * @Date 2019/12/2
 */
@Data
public class StandardOrderDetail {

    /**
     * 订单快照URL
     */
    private String snapshot_url;

    /**
     * 子订单编号
     */
    private String oid;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品图片的绝对路径
     */
    private String pic_path;

    /**
     * 退款状态。退款状态。可选值
     * WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意)
     * WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货)
     * WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货)
     * SELLER_REFUSE_BUYER(卖家拒绝退款)
     * CLOSED(退款关闭)
     * SUCCESS(退款成功)
     */
    private String refund_status;

    /**
     * 订单状态可选值:
     *     TRADE_NO_CREATE_PAY(没有创建支付宝交易)
     *     WAIT_BUYER_PAY(等待买家付款)
     *     WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
     *     WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
     *     TRADE_BUYER_SIGNED(买家已签收,货到付款专用)
     *     TRADE_FINISHED(交易成功)
     *     TRADE_CLOSED(付款以后用户退款成功，交易自动关闭)
     *     TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
     *     PAY_PENDING(国际信用卡支付付款确认中)
     */
    private String status;

    /**
     * 购买数量。取值范围:大于零的整数
     */
    private String num;

    /**
     * 商品数字ID
     */
    private String num_iid;

    /**
     * 商家外部编码(可与商家外部系统对接)。
     * 外部商家自己定义的商品Item的id，
     * 可以通过taobao.items.custom.get获取商品的Item的信息
     */
    private String outer_iid;

    /**
     * 商品的最小库存单位Sku的id
     */
    private String sku_id;

    /**
     * 外部网店自己定义的Sku编号
     */
    private String outer_sku_id;

    /**
     * 订单超时到期时间。格式:yyyy-MM-dd HH:mm:ss
     */
    private String timeout_action_time;

    /**
     * 交易商品对应的类目ID
     */
    private String cid;

    /**
     * 商品价格。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String price;

    /**
     * 应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）。
     * 精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String total_fee;

    /**
     * 子订单实付金额。精确到2位小数，单位:元。如:200.07，表示:200元7分。
     * 对于多子订单的交易，计算公式如下：payment = price * num + adjust_fee - discount_fee ；
     * 单子订单交易，payment与主订单的payment一致，对于退款成功的子订单，
     * 由于主订单的优惠分摊金额，会造成该字段可能不为0.00元。
     * 建议使用退款前的实付金额减去退款单中的实际退款金额计算。
     */
    private String payment;

    /**
     * 子订单级订单优惠金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String discount_fee;

    /**
     * 手工调整金额.格式为:1.01;单位:元;精确到小数点后两位.
     */
    private String adjust_fee;

    /**
     * 分摊之后的实付金额
     */
    private String divide_order_fee;
    /**
     * 优惠分摊
     */
    private String part_mjz_discount;


    /**
     * 子订单发货的快递公司名称
     */
    private String logistics_company;

    /**
     * 子订单所在包裹的运单号
     */
    private String invoice_no;
    /**
     * 发货的仓库编码
     */
    private String store_code;

    /**
     * 是否发货
     */
    private String is_sh_ship;

}