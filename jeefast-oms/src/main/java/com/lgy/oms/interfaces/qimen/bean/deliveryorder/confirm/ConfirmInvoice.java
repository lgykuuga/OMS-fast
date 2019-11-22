package com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 发货确认发票信息
 *
 * @author LGy
 */
@XStreamAlias("invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmInvoice {

    /**
     * 发票类型, string (50) , VINVOICE=增值税普通发票, EVINVOICE=电子增票,
     * 条件必填 (条件为invoiceFlag为Y)
     */
    private String type;
    /**
     * 发票抬头
     */
    private String header;
    /**
     * 发票总金额
     */
    private String amount;
    /**
     * 发票内容
     */
    private String content;

    /**
     * 订单详情
     */
    private ConfirmDetail detail;

    /**
     * 发票代码(纳税企业的标识)
     */
    private String code;


    /**
     * 税号
     */
    private String taxNumber;

    /**
     * 发票号码(纳税企业内部的发票号)
     */
    private String number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ConfirmDetail getDetail() {
        return detail;
    }

    public void setDetail(ConfirmDetail detail) {
        this.detail = detail;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
