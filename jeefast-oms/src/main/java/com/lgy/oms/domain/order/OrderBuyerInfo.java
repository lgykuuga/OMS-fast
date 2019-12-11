package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单买家信息表 oms_order_buyerinfo
 *
 * @author lgy
 * @date 2019-10-22
 */
@Data
@TableName("oms_order_buyerinfo")
public class OrderBuyerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单编码
     */
    private String orderId;

    /**
     * 来源单号
     */
    private String sourceId;

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
    private String buyerCardID;

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
    private String consigneeCardID;

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

}
