package com.lgy.oms.interfaces.rds.bean;

import lombok.Data;

/**
 * @Description 淘宝RDS推送库订单报文对象
 * @Author LGy
 * @Date 2019/12/4
 */
@Data
public class RdsOrder {

    private String pic_path;
    private String num_iid;
    private String price;
    private String cid;
    private String title;
    private String buyer_nick;
    private String snapshot_url;
    private String oid;
    private String status;
    private String num;
    private String order_from;
    private String total_fee;
    private String payment;
    private String discount_fee;
    private String adjust_fee;
    private String shipping_type;
    private boolean is_daixiao;
    private String part_mjz_discount;
    private String outer_iid;
}