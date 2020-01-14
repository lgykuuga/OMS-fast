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
import com.lgy.oms.dao.TraceLogMongoDB;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.mapper.TraceLogMapper;
import com.lgy.oms.service.ITraceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
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
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(DateUtils.getNowDate());
        }
        traceLogMapper.add(entity);
    }

    @Override
    public void batchAdd(List<TraceLog> list) {
        for (TraceLog traceLog : list) {
            if (traceLog.getCreateTime() == null) {
                traceLog.setCreateTime(DateUtils.getNowDate());
            }
            traceLogMapper.add(traceLog);
        }
    }

    @Override
    @MongoDB(document = OrderModuleConstants.TRACE_LOG_MongoDB, method = Method.GET)
    public List<TraceLog> get(TraceLog traceLog) {

        QueryWrapper<TraceLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(traceLog.getOrderId())) {
            queryWrapper.eq("order_id", traceLog.getOrderId());
        }
        if (StringUtils.isNotEmpty(traceLog.getModule())) {
            queryWrapper.eq("module", traceLog.getModule());
        }
        if (traceLog.getLevel() != null) {
            queryWrapper.eq("level", traceLog.getLevel());
        }
        return this.list(queryWrapper);
    }

}