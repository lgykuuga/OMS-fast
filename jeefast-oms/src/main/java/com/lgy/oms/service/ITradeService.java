package com.lgy.oms.service;

import com.lgy.oms.domain.Trade;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 交易订单 服务层
 *
 * @author lgy
 * @date 2019-10-15
 */
public interface ITradeService extends IService<Trade> {

    /**
     * 判断订单是否存在
     * @param tid 平台交易单号
     * @param shop 店铺
     * @param valid 是否查询有效订单
     * @return
     */
    List<Trade> checkOrderExist(String tid, String shop, boolean valid);

    /**
     * 预览订单报文
     * @param id 订单ID
     * @return
     */
    String previewOrder(Long id);
}