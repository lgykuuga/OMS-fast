package com.lgy.oms.biz.impl.stock;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.constant.ResponseCode;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.biz.*;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StandardOrderData;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.TradeParamDTO;
import com.lgy.oms.domain.order.*;
import com.lgy.oms.enums.order.*;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrderDetail;
import com.lgy.oms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 获取库存Service实现
 * @Author LGy
 * @Date 2020/2/23
 */
@Service
public class CheckStockServiceImpl implements ICheckStockService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IStockService stockService;

    @Override
    public int getStockQty(String commodity, String warehouse, String owner) {
        return 0;
    }
}
