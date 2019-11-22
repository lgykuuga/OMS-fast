package com.lgy.oms.interfaces.qimen.service.erp2oms;


import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.inventory.query.InventoryQueryRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * taobao.qimen.inventory.query( 库存查询接口（多商品） )
 * https://open.taobao.com/api.htm?cid=20725&docId=25999&docType=2
 * ERP调用奇门的接口,查询商品的库存量
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.INVENTORY_QUERY)
public class QimenInventoryQueryServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(HashMap<String, String> paramMap) {

        QimenResponse response = new QimenResponse();
        InventoryQueryRequest request = null;

        /** 1. XML转换 检查参数 */
        try {
            request = JaxbUtil.converyToJavaBean(paramMap.get("data"), InventoryQueryRequest.class);
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
