package com.lgy.oms.domain.dto;

import com.lgy.common.utils.StringUtils;

import java.util.Date;

/**
 * @Description 审核请求参数DTO
 * @Author LGy
 * @Date 2020/1/7 17:36
 **/
public class AuditParamDTO {

    /**
     * 是否自动触发
     */
    private Boolean auto;

    /**
     * 是否组装数据
     */
    private Boolean install;

    /**
     * 是否强制审核
     */
    private Boolean enforce;
    /**
     * 是否校验信息
     */
    private Boolean checkInfo;
    /**
     * 是否参与活动
     */
    private Boolean active;

    /**
     * 是否参与合并订单
     */
    private Boolean merge;
    /**
     * 是否拆分订单
     */
    private Boolean split;
    /**
     * 延迟配货时间
     */
    private Date delayDistributionTime;
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

    public AuditParamDTO() {
        this.auto = true;
        this.install = true;
        this.enforce = false;
        this.checkInfo = false;
        this.active = false;
        this.merge = false;
        this.split = false;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(Boolean checkInfo) {
        this.checkInfo = checkInfo;
    }

    public Boolean getMerge() {
        return merge;
    }

    public void setMerge(Boolean merge) {
        this.merge = merge;
    }

    public Boolean getSplit() {
        return split;
    }

    public void setSplit(Boolean split) {
        this.split = split;
    }

    public Date getDelayDistributionTime() {
        return delayDistributionTime;
    }

    public void setDelayDistributionTime(Date delayDistributionTime) {
        this.delayDistributionTime = delayDistributionTime;
    }

    public Boolean getEnforce() {
        return enforce;
    }

    public void setEnforce(Boolean enforce) {
        this.enforce = enforce;
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("审核参数:")
                .append("自动审核:").append(auto)
                .append(";强制审核:").append(enforce)
                .append(";校验信息:").append(checkInfo)
                .append(";参与活动:").append(active)
                .append(";参与合并订单:").append(merge)
                .append(";参与拆分订单:").append(auto);
                if (delayDistributionTime != null) {
                    stringBuffer.append(";延迟配货时间:").append(delayDistributionTime);
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

    public Boolean getInstall() {
        return install;
    }

    public void setInstall(Boolean install) {
        this.install = install;
    }
}
