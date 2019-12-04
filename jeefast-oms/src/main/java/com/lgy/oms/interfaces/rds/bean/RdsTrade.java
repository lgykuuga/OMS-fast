package com.lgy.oms.interfaces.rds.bean;


import lombok.Data;

/**
 * @Description RDS推送库报文对象
 * @Author LGy
 * @Date 2019/12/4
 */
@Data
public class RdsTrade {

    private String seller_nick;
    private String seller_type;
    private String pic_path;
    private String num_iid;
    private String price;
    private String cid;
    private String title;
    private String receiver_zip;
    private String receiver_name;
    private String receiver_state;
    private String receiver_address;
    private String receiver_mobile;
    private String receiver_phone;
    private String receiver_city;
    private String receiver_district;
    private String payment;
    private String post_fee;
    private String has_post_fee;
    private String received_payment;
    private String tid;
    private String num;
    private String status;
    private String type;
    private String discount_fee;
    private String total_fee;
    private String created;
    private String pay_time;
    private String modified;
    private String shipping_type;
    private String adjust_fee;
    private String trade_from;
    private RdsService_tags service_tags;
    private String buyer_nick;
    private String buyer_memo;
    private String buyer_message;
    private String seller_memo;
    private RdsOrders orders;


}