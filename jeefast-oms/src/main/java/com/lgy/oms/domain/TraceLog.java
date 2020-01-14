package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 轨迹日志 oms_trace_log
 * <p>
 * 标准版用MySQL,增强版用mongoDB
 *
 * @author lgy
 * @date 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_trace_log")
@Document(collection = "oms_trace_log")
public class TraceLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模块编码
     *
     * @see com.lgy.oms.constants.OrderModuleConstants
     */
    private String module;

    /**
     * 订单流水编号
     */
    private String orderId;

    /**
     * 操作类型
     *
     * @see com.lgy.oms.constants.OrderOperateType
     */
    private String type;

    /**
     * 日志级别
     *
     * @see com.lgy.oms.constants.TraceLevelType
     */
    private Integer level;

    /**
     * 内容
     */
    private String content;
    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public TraceLog(String module, String orderId, String type, Integer level, String content) {
        this.module = module;
        this.orderId = orderId;
        this.type = type;
        this.level = level;
        this.content = content;
    }

    @Override
    public String toString() {

        return new StringBuffer("TraceLog{module='").append(module)
                .append(", orderId='").append(module)
                .append(", type='").append(type)
                .append(", level='").append(level)
                .append(", content='").append(content)
                .append(", createBy='").append(createBy)
                .append(", createTime='").append(createTime).toString();

    }
}
