package com.lgy.oms.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.OrderDTO;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.business.IAsyncExecuteOrderService;
import com.lgy.oms.service.business.ICancelOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 异步处理订单Service实现
 * @Author LGy
 * @Date 2019/10/15 14:34
 **/
@Configuration
@EnableAsync
@Service
public class AsyncExecuteOrderServiceImpl implements IAsyncExecuteOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ITradeService tradeService;

    @Autowired
    ICancelOrderService cancelOrderService;

    @Async
    @Override
    public void updateOrderStatus(List<OrderDTO> updateList) {
        logger.debug("开始异步处理:平台状态变更,更新订单√");
        onlyUpdateOrder(updateList);
        logger.debug("完成异步处理:平台状态变更,更新订单√");
    }

    @Async
    @Override
    public void cancelOrderStatus(List<OrderDTO> cancelList) {
        for (OrderDTO orderBean : cancelList) {
            logger.debug("开始异步处理:平台订单状态为交易已关闭,取消订单");
            Map<String, Object> map = new HashMap<>(1);
            map.put("remark", "平台订单状态为交易已关闭,取消订单");
            orderBean.setParams(map);
            cancelOrderService.cancelOrder(orderBean);
            logger.debug("完成异步处理:平台订单状态为交易已关闭,取消订单");
        }
    }

    /**
     * 在订单列表接口更新订单订单状态和平台时间,不调用订单详情接口
     *
     * @param updateList 待更新订单列表
     */
    private void onlyUpdateOrder(List<OrderDTO> updateList) {

        for (OrderDTO orderDTO : updateList) {
            //判断有效订单是否存在订单池
            List<Trade> trades = tradeService.checkOrderExist(orderDTO.getTid(), orderDTO.getShop(), true);
            if (trades != null && trades.size() > 0) {
                for (Trade trade : trades) {
                    if (orderDTO.getPlatformModified().getTime() > trade.getModified().getTime()) {
                        //最新订单的平台最后更新时间 大于 系统订单平台最后更新时间,则进行更新
                        UpdateWrapper<Trade> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("tid", orderDTO.getTid());
                        Trade newTrade = new Trade();
                        //平台更新时间
                        newTrade.setModified(orderDTO.getPlatformModified());
                        //平台订单状态
                        newTrade.setStatus(orderDTO.getPlatformState());
                        //更新
                        tradeService.update(newTrade, updateWrapper);
                        logger.info("订单[{}]检测到平台状态为[{}]", orderDTO.getTid(), orderDTO.getPlatformState());
                    } else {
                        logger.info("订单[{}]状态为:[{}].平台订单更新时间[{}]<=系统订单更新时间[{}],跳过更新", orderDTO.getTid(), orderDTO.getPlatformState(),
                                orderDTO.getPlatformModified(), trade.getModified());
                    }
                }
            } else {
                logger.info("订单[{}]平台状态为:[{}].该订单不存在于系统", orderDTO.getTid(), orderDTO.getPlatformState());
            }
        }

    }

}
