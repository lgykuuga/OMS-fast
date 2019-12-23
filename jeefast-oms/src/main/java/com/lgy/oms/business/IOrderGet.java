package com.lgy.oms.business;


import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.interfaces.common.dto.OrderDTO;

import java.util.Date;
import java.util.List;

/**
 * @Description 订单获取接口调用
 * @Author LGy
 * @Date 2019/10/14
 */
public interface IOrderGet {

    /**
     * 获取订单列表接口
     *
     * @param shopInterfaces 店铺接口信息
     * @param beginDate      开始时间
     * @param endDate        结束时间
     * @return
     */
    CommonResponse<List<OrderDTO>> orderListDownload(ShopInterfaces shopInterfaces, Date beginDate, Date endDate);


    /**
     * 下载单笔订单详情
     *
     * @param shopInterfaces 店铺接口
     * @param orderDTO       订单传输对象
     * @return
     */
    CommonResponse<Trade> orderFullInfoDownLoad(ShopInterfaces shopInterfaces, OrderDTO orderDTO);

    /**
     * 根据时间获取订单详情列表接口
     *
     * @param shopInterfaces 店铺接口信息
     * @param beginDate      开始时间
     * @param endDate        结束时间
     * @return
     */
    CommonResponse<List<Trade>> orderFullInfoListDownload(ShopInterfaces shopInterfaces, Date beginDate, Date endDate);


}
