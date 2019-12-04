package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StandardOrderData;

/**
 * 交易订单快照 服务层
 *
 * @author lgy
 * @date 2019-12-03
 */
public interface ITradeStandardService extends IService<StandardOrderData> {

    /**
     * 获取最新的订单快照信息
     * @param tid
     * @return
     */
    StandardOrderData getLatestStandardOrderData(String tid);
}