package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.StrategyDistributionWarehouseWeight;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.mapper.StrategyDistributionWarehouseWeightMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseWeightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 配货策略分仓重量规则 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseWeightServiceImpl extends ServiceImpl<StrategyDistributionWarehouseWeightMapper, StrategyDistributionWarehouseWeight> implements IStrategyDistributionWarehouseWeightService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<StrategyDistributionWarehouseWeight> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseWeight> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseWeight entity = new StrategyDistributionWarehouseWeight();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseWeight rule = new StrategyDistributionWarehouseWeight();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

    @Override
    public List<String> getWeightWarehouse(List<String> warehouseList, OrderMain orderMain, String gco,
                                           StrategyDistributionWarehouseRule warehouseRule) {

        List<StrategyDistributionWarehouseWeight> weightList = getStrategyByGco(gco);
        // 当条件为空是，返回原仓库信息
        if (weightList == null || weightList.isEmpty()) {
            return warehouseList;
        }

        //添加到达区域优先级最高的仓库
        List<StrategyDistributionWarehouseWeight> toArriveList = getToArriveList(weightList, orderMain);
        if (toArriveList == null || toArriveList.isEmpty()) {
            return warehouseList;
        }
        String warehouse = toArriveList.get(0).getWarehouse();
        if (Constants.YES.equals(warehouseRule.getMust())) {
            logger.debug("订单[{}]根据到达区域重量必须规则获取到指定仓库:[{}]", orderMain.getOrderId(), warehouse);
            warehouseList.clear();
            warehouseList.add(warehouse);
        } else {
            int i = 0;
            for (StrategyDistributionWarehouseWeight weight : toArriveList) {
                if (warehouseList.contains(weight.getWarehouse())) {
                    logger.debug("订单[{}]根据到达区域重量规则获取到优先级[{}]的仓库:[{}]", orderMain.getOrderId(), i, weight.getWarehouse());
                    //将到达区域匹配到的仓库放在优先级最高位,优先判断库存
                    warehouseList.remove(weight.getWarehouse());
                    warehouseList.add(i, weight.getWarehouse());
                    i++;
                }
            }
        }

        return warehouseList;
    }

    /**
     * 匹配重量分仓到达仓库
     *
     * @param weightList
     * @param orderMain
     * @return
     */
    private List<StrategyDistributionWarehouseWeight> getToArriveList(List<StrategyDistributionWarehouseWeight> weightList, OrderMain orderMain) {

        if (weightList.isEmpty()) {
            return null;
        }

        List<StrategyDistributionWarehouseWeight> results = new ArrayList<>();

        //对覆盖区域条件到达
        Map<String, List<StrategyDistributionWarehouseWeight>> toMap = weightList.stream().collect(Collectors.groupingBy(StrategyDistributionWarehouseWeight::getWarehouse));
        for (String warehouse : toMap.keySet()) {
            List<StrategyDistributionWarehouseWeight> toList = toMap.get(warehouse);
            //根据优先级从高到低排序
            toList.sort(Comparator.comparing(StrategyDistributionWarehouseWeight::getPriority));
            // 遍历指定仓库里的每条设置
            for (StrategyDistributionWarehouseWeight weight : toList) {
                // 如果该仓库有满足到的，添加进去，不需要匹配其他的设置，直接跳出循环
                if (isHitRule(weight, orderMain)) {
                    results.add(weight);
                    break;
                }
            }
        }
        if (!results.isEmpty()) {
            //根据优先级从高到低排序
            results.sort(Comparator.comparing(StrategyDistributionWarehouseWeight::getPriority));
            //取出符合优先级最高的仓库
            return results;
        }
        return null;

    }


    /**
     * 判断是否击中规则
     *
     * @param weight    规则设置
     * @param orderMain 订单信息
     * @return
     */
    private boolean isHitRule(StrategyDistributionWarehouseWeight weight, OrderMain orderMain) {
        //是否命中标识
        boolean flag = false;

        //设置条件国家
        String nation = weight.getNation();
        //设置条件省份
        String province = weight.getProvince();
        //设置条件市
        String city = weight.getCity();
        //设置条件区
        String district = weight.getDistrict();

        if (orderMain.getOrderBuyerinfo().getNation().contains(nation) || StringUtils.isEmpty(nation)) {

            if (StringUtils.isEmpty(province)) {
                //填入国家并击中,规则没填省份,即为该国家所有省份都到达,则当作击中
                flag = true;
            } else if (!orderMain.getOrderBuyerinfo().getProvince().contains(province)) {
                flag = false;
            } else {

                if (StringUtils.isEmpty(city)) {
                    //填入省份并击中,规则没填城市,即为该省份所有城市都到达,则当作击中
                    flag = true;
                } else if (!orderMain.getOrderBuyerinfo().getCity().contains(city)) {
                    flag = false;
                } else {

                    if (StringUtils.isEmpty(district)) {
                        //填入城市并击中,规则没填区域,即为该城市所有区域都到达,则当作击中
                        flag = true;
                    } else if (!orderMain.getOrderBuyerinfo().getDistrict().contains(district)) {
                        flag = false;
                    } else {
                        flag = true;
                    }

                }
            }
        }

        if (flag) {
            BigDecimal orderWeight = orderMain.getWeight();
            //判断订单重量是否在指定区域重量范围
            if (orderWeight.compareTo(weight.getWeightMin()) >= 0 && orderWeight.compareTo(weight.getWeightMax()) <= 0) {
                flag = true;
            }
        }

        return flag;
    }


}