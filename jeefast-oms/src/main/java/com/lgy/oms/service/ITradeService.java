package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.Trade;

/**
 * 交易订单 服务层
 *
 * @author lgy
 * @date 2019-10-15
 */
public interface ITradeService extends IService<Trade> {

    /**
     * 判断订单是否存在
     *
     * @param tid   平台交易单号
     * @param shop  店铺
     * @param valid 是否查询有效订单
     * @return
     */
    Trade checkOrderExist(String tid, String shop, boolean valid);

    /**
     * 预览订单报文
     *
     * @param tid 订单号
     * @return
     */
    String previewOrder(String tid);

}