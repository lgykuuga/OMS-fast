package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.TradeMapper;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.service.ITradeService;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易订单 服务层实现
 *
 * @author lgy
 * @date 2019-10-15
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements ITradeService {

    @Autowired
    TradeMapper tradeMapper;

    @Override
    public List<Trade> checkOrderExist(String tid, String shop, boolean valid) {
        return tradeMapper.checkOrderExist(tid, shop, valid);
    }

    @Override
    public String previewOrder(Long id) {
        // 查询表信息
        Trade trade = tradeMapper.selectById(id);
        if (trade != null) {
            return trade.getResponse();
        }
        return "";
    }
}