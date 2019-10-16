package com.lgy.oms.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.OrderDTO;
import com.lgy.oms.enums.PlatformOrderStatusEnum;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.business.IAsyncExecuteOrderService;
import com.lgy.oms.service.business.ICreateOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 创建订单Service实现
 * @Author LGy
 * @Date 2019/10/14 19:38
 **/
@Service
public class CreateOrderServiceImpl implements ICreateOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ITradeService tradeService;

    @Autowired
    IAsyncExecuteOrderService asyncExecuteOrderService;

    @Override
    public Map<String, List<OrderDTO>> classifiedOrders(List<OrderDTO> orderList) {

        if (orderList == null || orderList.size() == 0) {
            logger.info("没有需要处理的订单");
            return null;
        }

        //定义需要下载保存的订单列表
        List<OrderDTO> saveList = new ArrayList<>();
        //定义需要下载更新的订单列表(更新未发货的订单)
        List<OrderDTO> updateList = new ArrayList<>();
        //定义需要取消的订单列表
        List<OrderDTO> cancelList = new ArrayList<>();
        //定义需要更新的订单列表(不需要下载订单详情,仅更新订单状态。)
        List<OrderDTO> onlyUpdateList = new ArrayList<>();

        for (OrderDTO orderBean : orderList) {
            //判断订单是否存在
            List<Trade> trades = tradeService.checkOrderExist(orderBean.getTid(), orderBean.getShop(), false);

            //根据平台订单状态判断流程,默认只下载等待卖家发货状态订单
            if (PlatformOrderStatusEnum.WAIT_BUYER_PAY.getValue() == orderBean.getPlatformState()) {
                //等待买家付款
                downloadSaveOrUpdateOrderList(orderBean, trades, saveList, updateList);
            } else if (PlatformOrderStatusEnum.WAIT_SELLER_SEND_GOODS.getValue() == orderBean.getPlatformState()) {
                //等待卖家发货
                downloadSaveOrUpdateOrderList(orderBean, trades, saveList, updateList);
            } else if (PlatformOrderStatusEnum.WAIT_BUYER_CONFIRM_GOODS.getValue() == orderBean.getPlatformState()) {
                //等待买家确认收货
                if (trades != null && trades.size() > 0) {
                    logger.info("订单[{}]状态为[{}],不下载该订单详情,只更新系统存在订单的平台状态", orderBean.getTid(), PlatformOrderStatusEnum.WAIT_BUYER_CONFIRM_GOODS.name());
                    onlyUpdateList.add(orderBean);
                } else {
                    logger.info("订单[{}]状态为[{}],系统不存在该订单,不下载该订单", orderBean.getTid(), PlatformOrderStatusEnum.WAIT_BUYER_CONFIRM_GOODS.name());
                }
            } else if (PlatformOrderStatusEnum.TRADE_BUYER_SIGNED.getValue() == orderBean.getPlatformState()) {
                //买家已签收
                if (trades != null && trades.size() > 0) {
                    logger.info("订单[{}]状态为[{}],不下载该订单,只更新系统存在订单的平台状态", orderBean.getTid(), PlatformOrderStatusEnum.TRADE_BUYER_SIGNED.name());
                    onlyUpdateList.add(orderBean);
                } else {
                    logger.info("订单[{}]状态为[{}],系统不存在该订单,不下载该订单", orderBean.getTid(), PlatformOrderStatusEnum.TRADE_BUYER_SIGNED.name());
                }
            } else if (PlatformOrderStatusEnum.TRADE_FINISHED.getValue() == orderBean.getPlatformState()) {
                //交易成功
                if (trades != null && trades.size() > 0) {
                    logger.info("订单[{}]状态为[{}],不下载该订单,只更新系统存在订单的平台状态", orderBean.getTid(), PlatformOrderStatusEnum.TRADE_FINISHED.name());
                    onlyUpdateList.add(orderBean);
                } else {
                    logger.info("订单[{}]状态为[{}],系统不存在该订单,不下载该订单", orderBean.getTid(), PlatformOrderStatusEnum.TRADE_FINISHED.name());
                }
            } else if (PlatformOrderStatusEnum.TRADE_CLOSED.getValue() == orderBean.getPlatformState()) {
                logger.info("订单[{}]状态为[{}],不下载该订单并尝试取消/冻结该订单", orderBean.getTid(), PlatformOrderStatusEnum.TRADE_CLOSED.name());
                //查询存在有效订单,如果存在,则取消/冻结订单
                cancelList.add(orderBean);
            } else {
                logger.info("订单[{}]状态[{}]未定义,不下载该订单", orderBean.getTid(), orderBean.getPlatformState());
            }
        }

        //异步处理 更新订单、取消订单 请求
        if (cancelList.size() > 0) {
            logger.debug("存在更新订单,异步处理数据");
            asyncExecuteOrderService.updateOrderStatus(onlyUpdateList);
        } else if (onlyUpdateList.size() > 0) {
            logger.debug("存在取消订单,异步处理数据");
            asyncExecuteOrderService.cancelOrderStatus(cancelList);
        }

        Map<String, List<OrderDTO>> map = new HashMap<>(2);
        map.put("save", saveList);
        map.put("update", updateList);
        return map;
    }

    /**
     * 判断下载新增订单或更新订单
     */
    private void downloadSaveOrUpdateOrderList(OrderDTO orderDTO, List<Trade> trades,
                                               List<OrderDTO> saveList, List<OrderDTO> updateList) {

        if (trades != null && trades.size() > 0) {
            for (Trade trade : trades) {
                if (orderDTO.getPlatformModified().getTime() > trade.getModified().getTime()) {
                    updateList.add(orderDTO);
                } else {
                    logger.info("订单[{}]平台系统更新时间不大于系统平台更新时间,不下载订单详情", orderDTO.getTid());
                }
            }
        } else {
            saveList.add(orderDTO);
        }
    }

    @Override
    public CommonResponse<Integer> updateTradeInfo(List<Trade> updateTradeList) {

        StringBuilder failReason = new StringBuilder();
        //成功更新订单数量
        int updateCount = 0;

        if (updateTradeList != null && updateTradeList.size() > 0) {
            for (Trade trade : updateTradeList) {
                List<Trade> trades = tradeService.checkOrderExist(trade.getTid(), trade.getShop(), false);
                if (trades != null && trades.size() > 0) {
                    for (Trade existTrade : trades) {

                        if (trade.getModified().getTime() > existTrade.getModified().getTime()) {
                            //最新订单的平台最后更新时间 大于 系统订单平台最后更新时间,则进行更新
                            UpdateWrapper<Trade> updateWrapper = new UpdateWrapper<>();
                            updateWrapper.eq("tid", existTrade.getTid());
                            //更新
                            boolean updateBoolean = tradeService.update(trade, updateWrapper);
                            if (updateBoolean) {
                                updateCount++;
                            }
                            logger.info("订单[{}]检测到平台状态为[{}]", existTrade.getTid(), trade.getStatus());
                        } else {
                            logger.info("订单[{}]状态为:[{}].平台订单更新时间[{}]<=系统订单更新时间[{}],跳过更新",
                                    existTrade.getTid(), trade.getStatus(), trade.getModified(), existTrade.getModified());
                        }
                    }
                } else {
                    logger.info("订单[{}]平台状态为:[{}].该订单不存在于系统", trade.getTid(), trade.getStatus());
                    failReason.append("订单");
                    failReason.append(trade.getTid());
                    failReason.append("平台状态为:");
                    failReason.append(trade.getStatus());
                    failReason.append(".该订单不存在于系统</br>");
                }
            }
        } else {
            failReason.append("没有更新订单信息");
        }

        if (StringUtils.isNotEmpty(failReason.toString())) {
            return new CommonResponse<Integer>().error(Constants.FAIL, failReason.toString());
        }

        return new CommonResponse<Integer>().ok(updateCount);
    }
}
