package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.enums.strategy.DistributionWarehouseRuleEnum;
import com.lgy.oms.mapper.StrategyDistributionWarehouseRuleMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 配货策略分仓规则列 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseRuleServiceImpl extends ServiceImpl<StrategyDistributionWarehouseRuleMapper, StrategyDistributionWarehouseRule> implements IStrategyDistributionWarehouseRuleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    @Override
    public List<StrategyDistributionWarehouseRule> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    @Override
    public CommonResponse<String> initRule(String gco) {

        //删除原有规则
        QueryWrapper<StrategyDistributionWarehouseRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        remove(queryWrapper);

        //分仓规则
        DistributionWarehouseRuleEnum[] values = DistributionWarehouseRuleEnum.values();

        List<StrategyDistributionWarehouseRule> list = new ArrayList<>(values.length);

        //重新生成规则
        for (int i = 0; i < values.length; i++) {
            StrategyDistributionWarehouseRule rule = new StrategyDistributionWarehouseRule();
            rule.setGco(gco);
            if (DistributionWarehouseRuleEnum.RULE_AVAILABLE.getCode() == i
                    || DistributionWarehouseRuleEnum.RULE_LOCK_STOCK.getCode() == i ) {
                //配货策略可用仓库和根据库存选择仓库必须开启
                rule.setMust(Integer.parseInt(Constants.YES));
            } else {
                rule.setMust(Integer.parseInt(Constants.NO));
            }
            rule.setPriority(i);
            rule.setRuleId(values[i].getCode());
            rule.setRemark(values[i].getRemark());
            list.add(rule);
        }

        boolean b = this.saveBatch(list);
        if (b) {
            logger.info("[{}]初始化分仓规则成功", ShiroUtils.getUserId());
            //保存轨迹
            traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.STRATEGY_DISTRIBUTION, gco,
                    OrderOperateType.RULE_INIT.getValue(), TraceLevelType.TRACE.getKey(), "初始化分仓策略成功"));
            return new CommonResponse<String>().ok("初始化分仓策略成功");
        }

        logger.error("[{}]初始化分仓规则失败", ShiroUtils.getUserId());
        //保存轨迹
        traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.STRATEGY_DISTRIBUTION, gco,
                OrderOperateType.RULE_INIT.getValue(), TraceLevelType.ABNORMAL.getKey(), "初始化分仓策略失败"));
        return new CommonResponse<String>().error(Constants.FAIL, "初始化分仓策略失败");
    }
}