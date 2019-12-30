package com.lgy.web.controller.oms.test;


import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.disruptor.TraceLogApi;
import com.lgy.oms.disruptor.TraceLogEvent;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.service.ITraceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 轨迹服务接口
 * @Author LGy
 * @Date 2019/12/31 9:59
 **/
@RestController
@RequestMapping("trace")
@Api("订单轨迹日志接口")
public class TraceLogController {

    /**
     * 订单轨迹服务Disruptor
     */
    @Autowired
    TraceLogApi traceLogApi;
    @Autowired
    ITraceLogService traceLogService;


    /**
     * 获取订单轨迹信息
     *
     * @param orderId 订单号
     * @return
     */
    @GetMapping("/getTraceLog")
    @ApiOperation(value = "获取订单状态信息", httpMethod = "GET")
    public CommonResponse<List<TraceLog>> get(String orderId) {

        if (StringUtils.isEmpty(orderId)) {
            return new CommonResponse<List<TraceLog>>().error(Constants.FAIL, "订单号不能为空");
        }

        List<TraceLog> traceLogs = traceLogService.get(orderId);
        if (StringUtils.isNotEmpty(traceLogs)) {
            return new CommonResponse<List<TraceLog>>().ok(traceLogs);
        }

        return new CommonResponse<List<TraceLog>>().error(Constants.FAIL, "获取失败");
    }

    /**
     * 新增订单轨迹日志
     *
     * @param traceLog 订单轨迹日志
     * @return
     */
    @CrossOrigin
    @PostMapping("/addTraceLog")
    @ApiOperation(value = "新增订单轨迹信息", httpMethod = "POST")
    public CommonResponse<String> add(TraceLog traceLog) {

        if (StringUtils.isEmpty(traceLog.getOrderId())) {
            return new CommonResponse<String>().error(Constants.FAIL, "订单号不能为空");
        }

        TraceLogEvent traceLogEvent = new TraceLogEvent();
        traceLogEvent.setTraceLog(traceLog);
        traceLogApi.addTraceLogAction(traceLogEvent);

        return new CommonResponse<String>().ok("发送成功");
    }

}
