package com.lgy.oms.interfaces.qimen.service.wms2oms;


import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * taobao.qimen.orderprocess.report( 订单流水通知接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=26003&docType=2
 * WMS调用奇门的接口,将订单在仓库的状态回传给ERP；场景说明：仓库仓内操作状态回传给ERP,
 * 比如打包操作完成时, 回传一个打 包完成的状态给到ERP, ERP自行决定如何处理。
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.ORDERPROCESS_REPORT)
public class QimenOrderProcessReportServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(HashMap<String, String> paramMap) {

        QimenResponse response = new QimenResponse();


        return response;

    }


}
