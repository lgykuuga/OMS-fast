package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.mapper.TradeMapper;
import com.lgy.oms.service.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易订单 服务层实现
 *
 * @author lgy
 * @date 2019-10-15
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements ITradeService {

    @Resource
    TradeMapper tradeMapper;

    @Override
    public Trade checkOrderExist(String tid, String shop, boolean valid) {

        List<Trade> trades = tradeMapper.checkOrderExist(tid, shop, valid);
        if (trades != null && trades.size() > 0) {
            return trades.get(0);
        }

        return null;
    }

    @Override
    public String previewOrder(String tid) {
        // 查询表信息
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        Trade trade = tradeMapper.selectOne(queryWrapper);
        if (trade != null) {
            return trade.getResponse();
        }
        return "";
    }
}