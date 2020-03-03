package com.lgy.oms.mapstruct;

import com.lgy.oms.disruptor.tracelog.TraceLogEvent;
import com.lgy.oms.domain.TraceLog;
import org.mapstruct.Mapper;

/**
 * @Description 订单轨迹 mapstruct, DTO->DO
 * @Author LGy
 * @Date 2020/3/3 17:40
 **/
@Mapper(componentModel = "spring")
public interface TraceLogMapStruct {

    /**
     * 订单轨迹对象转换
     *
     * @param traceLog 订单轨迹
     * @return
     */
    TraceLogEvent traceLog2Event(TraceLog traceLog);

}
