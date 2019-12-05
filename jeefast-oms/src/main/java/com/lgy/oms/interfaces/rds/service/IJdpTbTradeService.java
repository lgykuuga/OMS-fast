package com.lgy.oms.interfaces.rds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.interfaces.rds.bean.JdpTbTrade;

import java.util.Date;
import java.util.List;

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
     * @param tid            订单号
     * @return
     */
    CommonResponse<Trade> createOrder2Trade(ShopInterfaces shopInterfaces, String tid);

    /**
     * RDS推送库生成订单转换成交易订单
     * @param jdpTbTrade
     * @param shopInterfaces
     * @return
     */
    Trade convertTrade(JdpTbTrade jdpTbTrade, ShopInterfaces shopInterfaces);

    /**
     * 根据时间获取RDS订单列表
     *
     * @param shopInterfaces
     * @param bedt
     * @param endt
     * @return
     */
    List<JdpTbTrade> getJdpTbTradeListByTime(ShopInterfaces shopInterfaces, Date bedt, Date endt);
}