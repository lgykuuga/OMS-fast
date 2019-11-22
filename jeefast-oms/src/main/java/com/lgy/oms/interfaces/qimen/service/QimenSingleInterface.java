package com.lgy.oms.interfaces.qimen.service;

import com.lgy.common.core.domain.CommonResponse;

import java.util.Map;

/**
 * @Description 奇门接口抽象方法
 * @Author LGy
 * @Date 2019/11/21
 */
public interface QimenSingleInterface {

    /**
     * 接口执行方法
     *
     * @return
     */
    CommonResponse<Object> execute();

    /**
     * 数据转换
     *
     * @param data
     */
    void dataConvert(Object data);


    /**
     * 设置参数
     *
     * @param map
     */
    void setParams(Map<String, Object> map);
}
