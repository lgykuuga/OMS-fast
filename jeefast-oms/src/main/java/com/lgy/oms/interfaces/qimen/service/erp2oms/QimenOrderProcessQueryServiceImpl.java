package com.lgy.oms.interfaces.qimen.service.erp2oms;


import com.lgy.oms.interfaces.qimen.bean.QimenParam;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * taobao.qimen.orderprocess.query( 订单流水查询接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=27274&docType=2
 * ERP调用订单流水查询接口
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.ORDERPROCESS_QUERY)
public class QimenOrderProcessQueryServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(QimenParam qimenParam) {

        QimenResponse response = new QimenResponse();


        return response;

    }


}
