package com.lgy.oms.domain.dto;

/**
 * @Description 交易订单转单请求参数DTO
 * @Author LGy
 * @Date 2020/1/7 17:36
 **/
public class TradeParamDTO {

    /**
     * 是否自动触发
     */
    private Boolean auto;
    /**
     * 是否校验信息
     */
    private Boolean refund;
    /**
     * 备注
     */
    private String remark;

    public TradeParamDTO() {
        this.auto = false;
        this.refund = false;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Boolean getRefund() {
        return refund;
    }

    public void setRefund(Boolean refund) {
        this.refund = refund;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TradeParamDTO{" +
                "auto=" + auto +
                ", refund=" + refund +
                ", remark='" + remark + '\'' +
                '}';
    }
}
