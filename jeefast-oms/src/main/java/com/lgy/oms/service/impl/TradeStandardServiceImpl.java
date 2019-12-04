package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StandardOrderData;
import com.lgy.oms.mapper.TradeStandardMapper;
import com.lgy.oms.service.ITradeStandardService;
import org.springframework.stereotype.Service;

/**
 * 交易订单快照 服务层实现
 *
 * @author lgy
 * @date 2019-12-03
 */
@Service
public class TradeStandardServiceImpl extends ServiceImpl<TradeStandardMapper, StandardOrderData> implements ITradeStandardService {

    @Override
    public StandardOrderData getLatestStandardOrderData(String tid) {
        QueryWrapper<StandardOrderData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        queryWrapper.orderByDesc("modified");
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }
}