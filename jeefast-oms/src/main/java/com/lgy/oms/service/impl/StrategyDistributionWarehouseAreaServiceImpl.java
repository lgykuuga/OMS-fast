package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseArea;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.mapper.StrategyDistributionWarehouseAreaMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 配货策略分仓覆盖区域规则 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseAreaServiceImpl extends ServiceImpl<StrategyDistributionWarehouseAreaMapper, StrategyDistributionWarehouseArea> implements IStrategyDistributionWarehouseAreaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<StrategyDistributionWarehouseArea> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseArea> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    private List<StrategyDistributionWarehouseArea> getStrategyByGcoAndWarehouse(String gco, List<String> warehouseList) {
        QueryWrapper<StrategyDistributionWarehouseArea> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        queryWrapper.in("warehouse", warehouseList);
        queryWrapper.orderByDesc("priority");
        return this.list(queryWrapper);
    }

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseArea entity = new StrategyDistributionWarehouseArea();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseArea rule = new StrategyDistributionWarehouseArea();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

    @Override
    public List<String> getAreaWarehouse(List<String> warehouseList, OrderMain orderMain, String gco,
                                         StrategyDistributionWarehouseRule warehouseRule) {
        // 查询配货策略下的区域规则
        List<StrategyDistributionWarehouseArea> areaList = getStrategyByGcoAndWarehouse(gco, warehouseList);
        if (areaList == null || areaList.isEmpty()) {
            // 当条件为空是，返回原仓库信息
            return warehouseList;
        }

        //设置不到达区域,需要过滤的仓库
        List<String> removeWarehouse = new ArrayList<>();

        //对覆盖区域条件到达
        Map<String, List<StrategyDistributionWarehouseArea>> arriveMap = new HashMap<>(areaList.size());

        for (StrategyDistributionWarehouseArea area : areaList) {
            String warehouse = area.getWarehouse();
            if (Constants.YES.equals(area.getArrive())) {
                //设置到达仓库加入map
                if (arriveMap.get(warehouse) == null || arriveMap.get(warehouse).size() == 0) {
                    List<StrategyDistributionWarehouseArea> list = new ArrayList<>();
                    list.add(area);
                    arriveMap.put(warehouse, list);
                } else {
                    arriveMap.get(warehouse).add(area);
                }
            } else {
                //设置不到达只做排除仓库逻辑
                for (String originalWarehouse : warehouseList) {

                    if (warehouse.equals(originalWarehouse)) {
                        //判断该订单地址是否命中规则
                        if (isHitRule(area, orderMain)) {
                            //该仓库不到达区域,过滤.
                            removeWarehouse.add(originalWarehouse);
                        }

                    }
                }
            }
        }

        //删除不到达的仓库
        warehouseList.removeAll(removeWarehouse);
        //添加到达的优先级最高的仓库
        List<StrategyDistributionWarehouseArea> arriveWarehouseList = getArriveWarehouseList(arriveMap, orderMain);
        if (arriveWarehouseList != null) {
            String warehouse = arriveWarehouseList.get(0).getWarehouse();
            if (Constants.YES.equals(warehouseRule.getMust())) {
                logger.debug("订单[{}]根据到达区域必须规则获取到指定仓库:[{}]", orderMain.getOrderId(), warehouse);
                warehouseList.clear();
                warehouseList.add(warehouse);
            } else {
                int i = 0;
                for (StrategyDistributionWarehouseArea area : arriveWarehouseList) {
                    if (warehouseList.contains(area.getWarehouse())) {
                        logger.debug("订单[{}]根据到达区域规则获取到优先级[{}]的仓库:[{}]", orderMain.getOrderId(), i, area.getWarehouse());
                        //将到达区域匹配到的仓库放在优先级最高位,优先判断库存
                        warehouseList.remove(area.getWarehouse());
                        warehouseList.add(i, area.getWarehouse());
                        i++;
                    }
                }
            }
        }
        return warehouseList;
    }




    /**
     * 获取区域到的仓库
     *
     * @param arriveMap 到达区域仓库
     * @param orderMain 订单信息
     * @return
     */
    private List<StrategyDistributionWarehouseArea> getArriveWarehouseList(Map<String, List<StrategyDistributionWarehouseArea>> arriveMap, OrderMain orderMain) {
        if (arriveMap == null || arriveMap.isEmpty()) {
            return null;
        }

        List<StrategyDistributionWarehouseArea> areaList = new ArrayList<>(arriveMap.size());

        // 先遍历所有的仓库覆盖区域设置
        for (String whco : arriveMap.keySet()) {
            List<StrategyDistributionWarehouseArea> toList = arriveMap.get(whco);
            //根据优先级从高到低排序
            toList.sort(Comparator.comparing(StrategyDistributionWarehouseArea::getPriority));
            // 遍历指定仓库里的每条设置
            for (StrategyDistributionWarehouseArea area : toList) {

                if (isHitRule(area, orderMain)) {
                    areaList.add(area);
                    // 如果该仓库有满足到的，添加进去，不需要匹配其他的设置，直接跳出循环
                    break;
                }
            }
        }

        if (!areaList.isEmpty()) {
            //根据优先级从高到低排序
            areaList.sort(Comparator.comparing(StrategyDistributionWarehouseArea::getPriority));
            //取出符合优先级最高的仓库
            return areaList;
        }

        return null;
    }

    /**
     * 判断是否击中规则
     *
     * @param area      区域设置
     * @param orderMain 订单信息
     * @return
     */
    private boolean isHitRule(StrategyDistributionWarehouseArea area, OrderMain orderMain) {

        //设置条件国家
        String nation = area.getNation();
        //设置条件省份
        String province = area.getProvince();
        //设置条件市
        String city = area.getCity();
        //设置条件区
        String district = area.getDistrict();

        if (orderMain.getOrderBuyerinfo().getNation().contains(nation) || StringUtils.isEmpty(nation)) {

            if (StringUtils.isEmpty(province)) {
                //填入国家并击中,规则没填省份,即为该国家所有省份都到达,则当作击中
                return true;
            } else if (!orderMain.getOrderBuyerinfo().getProvince().contains(province)) {
                return false;
            } else {

                if (StringUtils.isEmpty(city)) {
                    //填入省份并击中,规则没填城市,即为该省份所有城市都到达,则当作击中
                    return true;
                } else if (!orderMain.getOrderBuyerinfo().getCity().contains(city)) {
                    return false;
                } else {

                    if (StringUtils.isEmpty(district)) {
                        //填入城市并击中,规则没填区域,即为该城市所有区域都到达,则当作击中
                        return true;
                    } else if (!orderMain.getOrderBuyerinfo().getDistrict().contains(district)) {
                        return false;
                    } else {
                        return true;
                    }

                }
            }
        }

        return false;
    }


}