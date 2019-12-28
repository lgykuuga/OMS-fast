package com.lgy.oms.disruptor;

/**
 * 
 *
 * @title: LongEvent.java
 * @package cn.gov.gzst.sms.common.disruptor.sms
 * @description: 内容传递
 * @author: 王存见
 * @date: 2017年6月7日 下午11:17:40
 * @version V1.0
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 *
 */

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

	/**
	 * 轨迹日志回调
	 */
	private TraceLogHandlerCallBack handlerCallBack;

	public TraceLog getTraceLog() {
		return traceLog;
	}

	public void setTraceLog(TraceLog traceLog) {
		this.traceLog = traceLog;
	}

	public TraceLogHandlerCallBack getHandlerCallBack() {
		return handlerCallBack;
	}

	public void setHandlerCallBack(TraceLogHandlerCallBack handlerCallBack) {
		this.handlerCallBack = handlerCallBack;
	}
}