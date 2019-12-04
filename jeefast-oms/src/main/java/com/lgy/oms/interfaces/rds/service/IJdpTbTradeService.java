package com.lgy.oms.interfaces.rds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.interfaces.common.dto.OrderDTO;
import com.lgy.oms.interfaces.rds.bean.JdpTbTrade;

/**
 * 订单推送库 服务层
 *
 * @author lgy
 * @date 2019-12-04
 */
public interface IJdpTbTradeService extends IService<JdpTbTrade> {

    /**
     * 获取RDS推送库订单数据
     *
     * @param tid 平台单号
     * @return
     */
    JdpTbTrade selectJdpTbTrade(String tid);


    /**
     * 从RDS推送库生成订单至交易订单模块
     *
     * @param shopInterfaces 店铺接口信息
     * @param orderDTO       订单信息
     * @return
     */
    CommonResponse<Trade> createOrder2Trade(ShopInterfaces shopInterfaces, OrderDTO orderDTO);

}