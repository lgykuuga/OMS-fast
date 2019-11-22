package com.lgy.oms.interfaces.kjy.service;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;

import java.util.List;

/**
 * @Description 调用跨境翼接口
 * @Author LGy
 * @Date 2019/10/14
 */
public interface IKJYService {

    /**
     * 获取订单详情列表
     *
     * @param shopInterfaces 店铺接口设置
     * @param beginDate      开始时间
     * @param endDate        结束时间
     * @return
     */
    CommonResponse<List<Trade>> getOrderFullInfoList(ShopInterfaces shopInterfaces, String beginDate, String endDate);


}
