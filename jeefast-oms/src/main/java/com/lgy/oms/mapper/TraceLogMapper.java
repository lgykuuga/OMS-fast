package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.TraceLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单轨迹服务 数据层
 *
 * @author lgy
 * @date 2019-12-26
 */
public interface TraceLogMapper extends BaseMapper<TraceLog> {

    /**
     * 增加订单操作记录
     *
     * @param entity
     */
    void add(TraceLog entity);

    /**
     * 批量增加订单操作记录---建议单批次不要超过 100条
     *
     * @param list
     */
    void batchAdd(List<TraceLog> list);


}