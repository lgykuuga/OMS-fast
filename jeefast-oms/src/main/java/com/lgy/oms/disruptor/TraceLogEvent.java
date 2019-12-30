package com.lgy.oms.disruptor;


import com.lgy.oms.domain.TraceLog;

/**
 * @Description 订单轨迹内容传递
 * @Author LGy
 * @Date 2019/12/27
 */
public class TraceLogEvent {

	/**
	 * 轨迹日志
	 */
	private TraceLog traceLog;

	public TraceLog getTraceLog() {
		return traceLog;
	}

	public void setTraceLog(TraceLog traceLog) {
		this.traceLog = traceLog;
	}
}