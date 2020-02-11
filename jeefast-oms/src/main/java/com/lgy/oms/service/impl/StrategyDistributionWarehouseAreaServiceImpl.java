package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseArea;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.mapper.StrategyDistributionWarehouseAreaMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseAreaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配货策略分仓覆盖区域规则 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseAreaServiceImpl extends ServiceImpl<StrategyDistributionWarehouseAreaMapper, StrategyDistributionWarehouseArea> implements IStrategyDistributionWarehouseAreaService {

    @Override
    public List<StrategyDistributionWarehouseArea> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseArea> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
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
        // 查询配货策略下的到达区域
        List<StrategyDistributionWarehouseArea> areaList = getStrategyByGco(gco);
        if (areaList == null || areaList.isEmpty()) {
            // 当条件为空是，返回原仓库信息
            return warehouseList;
        }

        // 根据审单单号查询订单信息
        OdinBean odin = odinService.findOne("odid", phma.getSoid());

        //设置不到达区域,需要过滤的仓库
        List<String> removeWarehouse = new ArrayList<>();

        //对覆盖区域条件到达
        Map<String, List<StrategyDistributionWarehouseArea>> toMap = new HashMap<>(areaList.size());

        for (StrategyDistributionWarehouseArea area : areaList) {
            String warehouse = area.getWarehouse();
            if (Constants.YES.equals(area.getArrive())) {
                //设置到达仓库逻辑
                if (toMap.get(warehouse) == null || toMap.get(warehouse).size() == 0) {
                    List<StrategyDistributionWarehouseArea> list = new ArrayList<>();
                    list.add(area);
                    toMap.put(warehouse, list);
                } else {
                    toMap.get(warehouse).add(area);
                }
            } else {
                //设置不到达只做排除仓库逻辑
                for (String originalWarehouse : warehouseList) {
                    //判断该订单地址是否不到达该仓库
                    if (warehouse.equals(originalWarehouse)) {
                        if (!isArriveWarehouse(area, orderMain)) {
                            //该仓库不到达区域,过滤.
                            removeWhco.add(originalWhco);
                        }

                    }
                }
            }
        }

        //删除不到达的仓库
        whcos.removeAll(removeWhco);
        //添加到达的优先级最高的仓库
        List<WharBean> toWhcoList = getToWhcoList(toMap, odin.getPrve(), odin.getCity(), odin.getArea());
        if (toWhcoList != null && toWhcoList.size() > 0) {
            String whco = toWhcoList.get(0).getWhco();
            if (waru.getIsbx() != null && waru.getIsbx() == 1) {
                logger.info("配货单[{}]根据到达区域规则获取到指定仓库:[{}]", phma.getBiid(), whco);
                whcos.clear();
                whcos.add(toWhcoList.get(0).getWhco());
            } else {
                int i = 0;
                for (WharBean whar : toWhcoList) {
                    if (whcos.contains(whar.getWhco())) {
                        logger.info("配货单[{}]根据到达区域规则获取到优先级[{}]的仓库:[{}]", phma.getBiid(), i, whar.getWhco());
                        //将到达区域匹配到的仓库放在优先级最高位,优先判断库存
                        whcos.remove(whar.getWhco());
                        whcos.add(i, whar.getWhco());
                        i++;
                    }
                }
            }
        }
        return whcos;
    }

    /**
     * 判断到达区域规则
     * @param area区域设置
     * @param orderMain 订单信息
     * @return
     */
    private boolean isArriveWarehouse(StrategyDistributionWarehouseArea area, OrderMain orderMain) {
        //默认到达
        boolean flag = true;


        if (Constants.NO.equals(area.getArrive())) {
            //设置不到

            //设置条件国家
            String nation = area.getNation();
            //设置条件省份
            String province = area.getProvince();
            //设置条件市
            String city = area.getCity();
            //设置条件区
            String district = area.getDistrict();

            if (orderMain.getOrderBuyerinfo().getNation().contains(nation)) {
                //国家设置相同
                if (orderMain.getOrderBuyerinfo().getProvince().contains(province)) {

                }
            }


            if (prve.indexOf(wharProv) > -1) {//订单省与规则省相同
                if (StringUtils.isBlank(wharCity)) {
                    flag = false;
                } else if (city.indexOf(wharCity) > -1) {
                    if (StringUtils.isBlank(wharArea) || area.indexOf(wharArea) > -1) {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }



}