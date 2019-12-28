package com.lgy.oms.disruptor;


import com.lgy.oms.domain.TraceLog;

import java.io.Serializable;

/**
 * @author LGy
 */
public interface TraceLogHandlerCallBack extends Serializable {

    /**
     * 处理结果
     *
     * @param traceLog
     */
    void onResult(TraceLog traceLog);
}
