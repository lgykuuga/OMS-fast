package com.lgy.oms.interfaces.kjy.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author LGy
 * @Date 2019/12/4
 */
@Data
public class Order {

    private String adjust_fee;
    private String buyer_rate;
    private String cid;
    private Date consign_time;
    private String discount_fee;
    private String divide_order_fee;
    private String invoice_no;
    private String is_daixiao;
    private String is_oversold;
    private String logistics_company;
    private String num;
    private String num_iid;
    private String oid;
    private String oid_str;
    private String order_attr;
    private String order_from;
    private String outer_sku_id;
    private String part_mjz_discount;
    private String payment;
    private String pic_path;
    private String price;
    private String refund_status;
    private String s_tariff_fee;
    private String seller_rate;
    private String seller_type;
    private String shipping_type;
    private String sku_id;
    private String sku_properties_name;
    private String snapshot_url;
    private String status;
    private String store_code;
    private String sub_order_tax_fee;
    private String sub_order_tax_promotion_fee;
    private String sub_order_tax_rate;
    private String tax_coupon_discount;
    private String tax_free;
    private String title;
    private String total_fee;

}