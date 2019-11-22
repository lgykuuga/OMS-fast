package com.lgy.oms.interfaces.ods.bean.response.order;

import java.util.List;

/**
 * 订单发票信息
 *
 * @author admin
 */
public class InvoiceBean {

    /**
     * 发票类型, string (50) ,
     * INVOICE=普通发票，
     * VINVOICE=增值税普通发票,
     * EVINVOICE=电子增票, 条件必填 (条件为invoiceFlag为Y)
     */
    private String type;
    /**
     * 发票抬头, string (200) ,  (条件为invoiceFlag为Y)
     */
    private String header;
    /**
     * 发票总金额, double (18, 2) ,  (条件为invoiceFlag为Y)
     */
    private Double amount;
    /**
     * 发票内容,string(500) ，不推荐使用
     */
    private String content;

    private List<InvoiceItemBean> invoices;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<InvoiceItemBean> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceItemBean> invoices) {
        this.invoices = invoices;
    }

}
