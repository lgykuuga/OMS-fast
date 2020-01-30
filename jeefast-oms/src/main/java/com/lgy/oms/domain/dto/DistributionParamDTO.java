package com.lgy.oms.domain.dto;

import com.lgy.common.utils.StringUtils;

import java.util.Date;

/**
 * @Description 配货请求参数DTO
 * @Author LGy
 * @Date 2020/1/7 17:36
 **/
public class DistributionParamDTO {

    /**
     * 是否自动触发
     */
    private Boolean auto;

    /**
     * 是否组装数据
     */
    private Boolean install;

    /**
     * 是否校验库存
     */
    private Boolean checkStock;

    /**
     * 是否拆分订单
     */
    private Boolean split;
    /**
     * 延迟配货单推送时间
     */
    private Date delaySendTime;
    /**
     * 仓库
     */
    private String warehouse;
    /**
     * 物流
     */
    private String logistics;
    /**
     * 物流单号
     */
    private String expressNumber;
    /**
     * 备注
     */
    private String remark;

    /**
     * 开始时间,用于统计耗时
     */
    private Long startTime;

    public DistributionParamDTO() {
        this.auto = true;
        this.install = true;
        this.checkStock = true;
        this.split = false;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Boolean getCheckStock() {
        return checkStock;
    }

    public void setCheckStock(Boolean checkStock) {
        this.checkStock = checkStock;
    }

    public Boolean getSplit() {
        return split;
    }

    public void setSplit(Boolean split) {
        this.split = split;
    }

    public Date getDelaySendTime() {
        return delaySendTime;
    }

    public void setDelaySendTime(Date delaySendTime) {
        this.delaySendTime = delaySendTime;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Boolean getInstall() {
        return install;
    }

    public void setInstall(Boolean install) {
        this.install = install;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("配货参数:")
                .append("自动配货:").append(auto)
                .append(";校验库存:").append(checkStock)
                .append(";参与拆分订单:").append(split);

        if (delaySendTime != null) {
            stringBuffer.append(";延迟配货单推送时间:").append(delaySendTime);
        }
        if (StringUtils.isNotEmpty(warehouse)) {
            stringBuffer.append(";选择仓库:").append(warehouse);
        }
        if (StringUtils.isNotEmpty(logistics)) {
            stringBuffer.append(";选择物流:").append(logistics);
        }
        if (StringUtils.isNotEmpty(expressNumber)) {
            stringBuffer.append(";物流单号:").append(expressNumber);
        }
        if (StringUtils.isNotEmpty(remark)) {
            stringBuffer.append(";备注:").append(remark);
        }
        return stringBuffer.toString();
    }

}
