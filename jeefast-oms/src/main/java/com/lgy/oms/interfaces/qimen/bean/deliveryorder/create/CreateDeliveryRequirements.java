package com.lgy.oms.interfaces.qimen.bean.deliveryorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 发货要求
 *
 * @author LGy
 */
@XStreamAlias("deliveryRequirements")
public class CreateDeliveryRequirements {

    /**
     * 投递时延要求,int, 1=工作日,2=节假日,101=当日达,102=次晨达,103=次日达, 104=预约达
     */
    private String scheduleType;
    /**
     * 要求送达日期, string (10) , YYYY-MM-DD
     */
    private String scheduleDay;
    /**
     * 投递时间范围要求(开始时间;格式：HH:MM:SS)
     */
    private String scheduleStartTime;
    /**
     * 投递时间范围要求(结束时间;格式：HH:MM:SS)
     */
    private String scheduleEndTime;
    /**
     * 发货服务类型，PTPS（普通配送），LLPS（冷链配送）
     */
    private String deliveryType;

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(String scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public String getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(String scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
