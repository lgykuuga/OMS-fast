package com.lgy.oms.interfaces.ods.service.impl;

import com.lgy.oms.interfaces.ods.bean.response.BaseResponse;
import com.lgy.oms.interfaces.ods.service.IODSService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 请求ODS接口实现
 * @Author LGy
 * @Date 2019/10/14 17:08
 **/
@Service
public class ODSServiceImpl implements IODSService {


    @Override
    public BaseResponse getOrderList(Map<String, Object> map) {
        return null;
    }

    @Override
    public BaseResponse getOrderFullInfo(Map<String, Object> map) {
        return null;
    }
}
