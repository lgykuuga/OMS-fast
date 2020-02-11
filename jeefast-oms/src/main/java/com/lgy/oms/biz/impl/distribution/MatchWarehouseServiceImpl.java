package com.lgy.oms.biz.impl.distribution;

import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.biz.IEventDrivenService;
import com.lgy.oms.biz.IFulfillService;
import com.lgy.oms.biz.IMatchWarehouseService;
import com.lgy.oms.biz.IOrderConvertService;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.strategy.DistributionWarehouseRuleEnum;
import com.lgy.oms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 履约订单服务实现
 * @Author LGy
 * @Date 2020/1/31
 */
@Service
public class MatchWarehouseServiceImpl implements IMatchWarehouseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    @Autowired
    IStrategyDistributionWarehouseAreaService areaService;

    @Autowired
    IStrategyDistributionWarehouseAvailableService availableService;

    @Autowired
    IStrategyDistributionWarehouseLogisticsService logisticsService;

    @Autowired
    IStrategyDistributionWarehouseSkuService skuService;

    @Autowired
    IStrategyDistributionWarehouseRuleService ruleService;

    @Autowired
    IStrategyDistributionWarehouseSpecialService specialService;

    @Autowired
    IStrategyDistributionWarehouseWeightService weightService;



    @Override
    public CommonResponse<List<String>> start(OrderMain orderMain, StrategyDistribution strategyDistribution, DistributionParamDTO param) {

        logger.info("订单[{}]开始配货策略[{}]分仓流程", orderMain.getOrderId(), strategyDistribution.getGco());

        //可用仓库有序集合
        List<String> warehouseList = new ArrayList<>();
        //返回消息
        StringBuilder rtnMessage = new StringBuilder();

        List<StrategyDistributionWarehouseRule> warehouseRules = ruleService.matchWarehouseGyRules(strategyDistribution.getGco());

        if (warehouseRules == null || warehouseRules.isEmpty()) {
            if (StringUtils.isNotEmpty(strategyDistribution.getWarehouse())) {
                logger.info("订单[{}]匹配策略[{}]没有初始化分仓规则,分配店铺策略默认仓库。", orderMain.getOrderId(), strategyDistribution.getGco());
                warehouseList.add(strategyDistribution.getWarehouse());
                return new CommonResponse<List<String>>().ok(warehouseList);
            } else {
                rtnMessage.append("订单").append(orderMain.getOrderId()).append("匹配策略")
                        .append(strategyDistribution.getGco())
                        .append("没有初始化分仓规则,且没有分配店铺策略默认仓库。");
                logger.error(rtnMessage.toString());
                return new CommonResponse<List<String>>().error(Constants.FAIL, rtnMessage.toString());
            }
        }

        for (StrategyDistributionWarehouseRule warehouseRule : warehouseRules) {
            //开启必须规则:排除其他仓库并执行必须规则
            if (Constants.ON.equals(warehouseRule.getMust())) {

                if (DistributionWarehouseRuleEnum.RULE_AVAILABLE.getCode().equals(warehouseRule.getRuleId())) {
                    //分仓策略-可用仓库
                    warehouseList = availableService.getAvailableWarehouse(strategyDistribution.getGco());
                    if (warehouseList == null || warehouseList.isEmpty()) {
                        rtnMessage.append("订单").append(orderMain.getOrderId()).append("匹配策略")
                                .append(strategyDistribution.getGco())
                                .append("可用仓库没有维护,分仓失败。");
                        logger.error(rtnMessage.toString());
                        return new CommonResponse<List<String>>().error(Constants.FAIL, rtnMessage.toString());
                    }
                } else if (DistributionWarehouseRuleEnum.RULE_SPECIAL.getCode().equals(warehouseRule.getRuleId())) {
                    //分仓策略-特定信息分仓
                    String warehouse = specialService.getSpecialWarehouse(orderMain, strategyDistribution.getGco());
                    if (StringUtils.isNotEmpty(warehouse)) {
                        //确定唯一仓库
                        if (warehouseList.contains(warehouse)) {
                            warehouseList.clear();
                            warehouseList.add(warehouse);
                        } else {
                            rtnMessage.append("订单").append(orderMain.getOrderId()).append("匹配策略")
                                    .append(strategyDistribution.getGco())
                                    .append("根据必须规则:特定信息分配仓库")
                                    .append(warehouse)
                                    .append("与其他规则冲突,请检查分仓规则设置");
                            logger.error(rtnMessage.toString());
                            return new CommonResponse<List<String>>().error(Constants.FAIL, rtnMessage.toString());
                        }
                    }
                } else if (DistributionWarehouseRuleEnum.RULE_AREA.getCode().equals(warehouseRule.getRuleId())) {
                    //分仓策略-到达区域分仓
                    warehouseList = areaService.getAreaWarehouse(warehouseList, orderMain, strategyDistribution.getGco(), warehouseRule);
                }

            }
        }

        return null;
    }


}
