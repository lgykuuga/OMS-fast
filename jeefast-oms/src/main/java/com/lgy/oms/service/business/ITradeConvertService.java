package com.lgy.oms.service.business;

import com.lgy.common.core.domain.CommonResponse;

import java.util.Map;

/**
 * @Description 交易订单转换成正式订单
 * @Author LGy
 * @Date 2019/12/2
 */
public interface ITradeConvertService {

    /**
     * 交易订单报文生成订单
     *
     * @param tid 平台交易订单号
     * @param map 其它参数
     * @return
     */
    CommonResponse<String> execute(String tid, Map<String, Object> map);


}
