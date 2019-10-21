package com.lgy.oms.service.business;

import com.lgy.oms.domain.dto.ods.response.BaseResponse;

import java.util.Map;

/**
 * @Description 调用ODS接口
 * @Author LGy
 * @Date 2019/10/14
 */
@Deprecated
public interface IODSService {

    /**
     * 获取订单列表
     *
     * @param map 请求参数
     * @return
     */
    @Deprecated
    BaseResponse getOrderList(Map<String, Object> map);

    /**
     * 获取单笔订单详情
     *
     * @param map 请求参数
     * @return
     */
    @Deprecated
    BaseResponse getOrderFullInfo(Map<String, Object> map);
}
