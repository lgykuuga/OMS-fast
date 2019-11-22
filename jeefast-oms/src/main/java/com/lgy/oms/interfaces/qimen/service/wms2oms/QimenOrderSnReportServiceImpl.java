package com.lgy.oms.interfaces.qimen.service.wms2oms;


import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * taobao.qimen.order.sn.report( 订单SN通知接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=27962&docType=2
 * WMS调用奇门的接口,在出库、发货、入库等场景下，ERP和WMS之间同步操作的SN列表
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.ORDER_SN_REPORT)
public class QimenOrderSnReportServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(HashMap<String, String> paramMap) {

        QimenResponse response = new QimenResponse();


        return response;

    }


}
