package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.StrategyDistributionWarehouseSpecial;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderTableEnum;
import com.lgy.oms.enums.strategy.ConditionEnum;
import com.lgy.oms.mapper.StrategyDistributionWarehouseSpecialMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseSpecialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 配货策略特定信息分仓 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseSpecialServiceImpl extends ServiceImpl<StrategyDistributionWarehouseSpecialMapper, StrategyDistributionWarehouseSpecial> implements IStrategyDistributionWarehouseSpecialService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<StrategyDistributionWarehouseSpecial> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseSpecial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    private List<StrategyDistributionWarehouseSpecial> getAvailableStrategyByGcoAndWarehouse(String gco, List<String> warehouseList) {
        QueryWrapper<StrategyDistributionWarehouseSpecial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        queryWrapper.in("warehouse", warehouseList);
        queryWrapper.orderByAsc("priority");
        return this.list(queryWrapper);
    }

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseSpecial entity = new StrategyDistributionWarehouseSpecial();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseSpecial rule = new StrategyDistributionWarehouseSpecial();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

    @Override
    public List<String> getSpecialWarehouse(List<String> warehouseList, OrderMain orderMain, String gco,
                                            StrategyDistributionWarehouseRule warehouseRule) {

        List<StrategyDistributionWarehouseSpecial> skuList = getAvailableStrategyByGcoAndWarehouse(gco, warehouseList);
        // 当条件为空是，返回原仓库信息
        if (skuList == null || skuList.isEmpty()) {
            return warehouseList;
        }

        //订单命中的规则
        List<StrategyDistributionWarehouseSpecial> toArriveList = getToArriveList(skuList, orderMain);
        if (toArriveList == null || toArriveList.isEmpty()) {
            return warehouseList;
        }

        if (Constants.YES.equals(warehouseRule.getMust())) {
            String warehouse = toArriveList.get(0).getWarehouse();
            logger.debug("订单[{}]根据sku仓库必须规则获取到指定仓库:[{}]", orderMain.getOrderId(), warehouse);
            warehouseList.clear();
            warehouseList.add(warehouse);
        } else {
            int i = 0;
            for (StrategyDistributionWarehouseSpecial special : toArriveList) {
                if (warehouseList.contains(special.getWarehouse())) {
                    logger.debug("订单[{}]根据sku仓库规则获取到优先级[{}]的仓库:[{}]", orderMain.getOrderId(), i, special.getWarehouse());
                    //将到达区域匹配到的仓库放在优先级最高位,优先判断库存
                    warehouseList.remove(special.getWarehouse());
                    warehouseList.add(i, special.getWarehouse());
                    i++;
                }
            }
        }

        return warehouseList;
    }


    /**
     * 获取命中规则List
     *
     * @param specialList 规则范围list
     * @param orderMain   订单信息
     * @return
     */
    private List<StrategyDistributionWarehouseSpecial> getToArriveList(List<StrategyDistributionWarehouseSpecial> specialList,
                                                                       OrderMain orderMain) {

        //命中规则标识
        boolean flag = false;
        //命中规则日志记录
        StringBuilder message = new StringBuilder();

        //命中的规则list
        List<StrategyDistributionWarehouseSpecial> warehouseSpecials = new ArrayList<>();

        for (StrategyDistributionWarehouseSpecial special : specialList) {

            if (OrderTableEnum.MAIN.getCode().equals(special.getField())) {
                //订单主信息
                flag = conditionValue(OrderTableEnum.MAIN, orderMain, special, message);

            } else if (OrderTableEnum.BUYER_INFO.getCode().equals(special.getField())) {
                //订单买家信息
                flag = conditionValue(OrderTableEnum.BUYER_INFO, orderMain.getOrderBuyerinfo(), special, message);

            } else if (OrderTableEnum.PAY_INFO.getCode().equals(special.getField())) {
                //订单支付信息
                flag = conditionValue(OrderTableEnum.PAY_INFO, orderMain.getOrderPayinfo(), special, message);

            } else if (OrderTableEnum.STATUS_INFO.getCode().equals(special.getField())) {
                //订单状态信息
                flag = conditionValue(OrderTableEnum.STATUS_INFO, orderMain.getOrderStatusinfo(), special, message);

            } else if (OrderTableEnum.TYPE_INFO.getCode().equals(special.getField())) {
                //订单类型信息
                flag = conditionValue(OrderTableEnum.TYPE_INFO, orderMain.getOrderTypeinfo(), special, message);

            } else if (OrderTableEnum.INTERCEPT_INFO.getCode().equals(special.getField())) {
                //订单拦截信息
                flag = conditionValue(OrderTableEnum.INTERCEPT_INFO, orderMain.getOrderInterceptInfo(), special, message);

            } else if (OrderTableEnum.DETAIL.getCode().equals(special.getField())) {
                //订单明细信息
                flag = conditionValue(OrderTableEnum.DETAIL, orderMain.getOrderDetails(), special, message);

            }

            if (flag) {
                warehouseSpecials.add(special);
                logger.debug(message.toString());
            }

        }
        return warehouseSpecials;

    }

    private boolean conditionValue(OrderTableEnum tableEnum, Object object,
                                   StrategyDistributionWarehouseSpecial special, StringBuilder message) {

        Object fieldValue = ReflectUtils.getFieldValue(object, special.getField());

        if (ConditionEnum.EQUAL.getCode().equals(special.getRequirement())) {
            //等于判断
            if (special.getValueCode().equals(fieldValue)) {
                message.append("命中规则字段【").append(special.getField()).append("】条件【")
                        .append(ConditionEnum.EQUAL.getName())
                        .append("】值:").append(fieldValue);
                return true;
            }
        } else if (ConditionEnum.NOT_EQUAL.getCode().equals(special.getRequirement())) {
            //不等于判断
            if (!special.getValueCode().equals(fieldValue)) {
                message.append("命中规则字段【").append(special.getField()).append("】条件【")
                        .append(ConditionEnum.NOT_EQUAL.getName())
                        .append("】值:").append(fieldValue);
                return true;
            }
        } else if (ConditionEnum.GREATER_THAN.getCode().equals(special.getRequirement())) {
            //大于判断
            message.append("未开发命中规则字段【").append(special.getField()).append("】条件【")
                    .append(ConditionEnum.GREATER_THAN.getName())
                    .append("】值:").append(fieldValue);
            return false;

        } else if (ConditionEnum.LESS_THAN.getCode().equals(special.getRequirement())) {
            //小于判断
            message.append("未开发命中规则字段【").append(special.getField()).append("】条件【")
                    .append(ConditionEnum.LESS_THAN.getName())
                    .append("】值:").append(fieldValue);
            return false;

        } else if (ConditionEnum.GREATER_THAN_OR_EQUAL.getCode().equals(special.getRequirement())) {
            //大于等于判断
            message.append("未开发命中规则字段【").append(special.getField()).append("】条件【")
                    .append(ConditionEnum.GREATER_THAN_OR_EQUAL.getName())
                    .append("】值:").append(fieldValue);
            return false;

        } else if (ConditionEnum.LESS_THAN_OR_EQUAL.getCode().equals(special.getRequirement())) {
            //小于等于判断
            message.append("未开发命中规则字段【").append(special.getField()).append("】条件【")
                    .append(ConditionEnum.LESS_THAN_OR_EQUAL.getName())
                    .append("】值:").append(fieldValue);
            return false;

        } else if (ConditionEnum.BETWEEN.getCode().equals(special.getRequirement())) {
            //两数之间判断
            message.append("未开发命中规则字段【").append(special.getField()).append("】条件【")
                    .append(ConditionEnum.BETWEEN.getName())
                    .append("】值:").append(fieldValue);
            return false;

        } else if (ConditionEnum.CONTAINS.getCode().equals(special.getRequirement())) {
            //包含判断
            String valueCode = special.getValueCode();
            String[] codeArray = valueCode.split(Constants.COMMA);
            for (String code : codeArray) {
                if (fieldValue != null && fieldValue.toString().contains(code)) {
                    message.append("命中规则字段【").append(special.getField()).append("】条件【")
                            .append(ConditionEnum.CONTAINS.getName())
                            .append("】值:").append(fieldValue);
                    return true;
                }
            }

        } else if (ConditionEnum.IN.getCode().equals(special.getRequirement())) {
            //在数组数据当中判断
            String valueCode = special.getValueCode();
            String[] codeArray = valueCode.split(Constants.COMMA);
            for (String code : codeArray) {
                if (fieldValue != null && fieldValue.toString().contains(code)) {
                    message.append("命中规则字段【").append(special.getField()).append("】条件【")
                            .append(ConditionEnum.IN.getName())
                            .append("】值:").append(fieldValue);
                    return true;
                }
            }


        }

        return false;
    }


}