package com.lgy.oms.interfaces.ods.service;


import com.lgy.oms.interfaces.ods.bean.response.BaseResponse;

import java.util.Map;

/**
 * @Description 调用ODS接口
 * @Author LGy
 * @Date 2019/10/14
 */
public interface IODSService {

    /**
     * 获取订单列表
     *
     * @param map 请求参数
     * @return
     */
    BaseResponse getOrderList(Map<String, Object> map);

    /**
     * 获取单笔订单详情
     *
     * @param map 请求参数
     * @return
     */
    BaseResponse getOrderFullInfo(Map<String, Object> map);
}
