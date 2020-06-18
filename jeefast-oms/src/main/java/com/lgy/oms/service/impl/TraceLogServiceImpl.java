package com.lgy.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.annotation.MongoDB;
import com.lgy.common.constant.Method;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.mapper.TraceLogMapper;
import com.lgy.oms.service.ITraceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 订单轨迹服务 服务层实现
 *
 * @author lgy
 * @date 2019-12-26
 */

@Service
public class TraceLogServiceImpl extends ServiceImpl<TraceLogMapper, TraceLog>
        implements ITraceLogService {

    private static Logger logger = LoggerFactory.getLogger(TraceLogServiceImpl.class);

    @Resource
    TraceLogMapper traceLogMapper;

    @Override
    @MongoDB(document = OrderModuleConstants.TRACE_LOG_MongoDB)
    public void add(TraceLog entity) {
        if (StringUtils.isEmpty(entity.getOrderId())) {
            logger.error("订单号不能为空[{}]", JSON.toJSONString(entity));
            throw new NullPointerException("订单号不能为空");
        }
        if (StringUtils.isEmpty(entity.getCreateBy())) {
            entity.setCreateBy(ShiroUtils.getSysUser().getUserName());
        }
        if (Objects.isNull(entity.getCreateTime())) {
            entity.setCreateTime(DateUtils.getNowDate());
        }
        traceLogMapper.add(entity);
    }

    @Override
    public void batchAdd(List<TraceLog> list) {
        for (TraceLog traceLog : list) {
            if (Objects.isNull(traceLog.getCreateTime())) {
                traceLog.setCreateTime(DateUtils.getNowDate());
            }
            //在循环里处理新增,是为了适配mongoDB逻辑.只有少量业务会用到该方法
            this.add(traceLog);
        }
    }

    @Override
    @MongoDB(document = OrderModuleConstants.TRACE_LOG_MongoDB, method = Method.GET)
    public List<TraceLog> get(TraceLog traceLog) {

        QueryWrapper<TraceLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(traceLog.getOrderId())) {
            queryWrapper.lambda().eq(TraceLog::getOrderId, traceLog.getOrderId());
        }
        if (StringUtils.isNotEmpty(traceLog.getModule())) {
            queryWrapper.lambda().eq(TraceLog::getModule, traceLog.getModule());
        }
        if (Objects.nonNull(traceLog.getLevel())) {
            queryWrapper.lambda().eq(TraceLog::getLevel, traceLog.getLevel());
        }
        return this.list(queryWrapper);
    }

}