package com.lgy.oms.interfaces.qimen.service.wms2oms;


import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.entryorder.EntryOrderConfirmRequest;
import com.lgy.oms.interfaces.qimen.bean.stockout.StockOutConfirmRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * taobao.qimen.stockout.confirm( 出库单确认接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=27143&docType=2
 * 货品出库后，WMS将状态回传给ERP
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.STOCKOUT_CONFIRM)
public class QimenStockoutConfirmServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(HashMap<String, String> paramMap) {

        QimenResponse response = new QimenResponse();
        StockOutConfirmRequest request = null;

        /** 1. XML转换 检查参数 */
        try {
            request = JaxbUtil.converyToJavaBean(paramMap.get("data"), StockOutConfirmRequest.class);
        } catch (Exception e) {
            response.setFlag(QimenConstants.FAILURE);
            response.setMessage("解析XML出错");
            return response;
        }

        if (request == null) {
            response.setFlag(QimenConstants.FAILURE);
            response.setMessage("请求内容(data)参数格式不正确");
            return response;
        }


        return response;

    }


}
