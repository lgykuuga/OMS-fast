package com.lgy.oms.interfaces.qimen.service.erp2oms;


import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.entryorder.EntryOrderCreateRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * taobao.qimen.entryorder.create( 入库单创建接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25991&docType=2
 * ERP调用接口，创建入库单;
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.ENTRYORDER_CREATE)
public class QimenEntryOrderCreateServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(HashMap<String, String> paramMap) {

        QimenResponse response = new QimenResponse();
        EntryOrderCreateRequest request = null;

        /** 1. XML转换 检查参数 */
        try {
            request = JaxbUtil.converyToJavaBean(paramMap.get("data"), EntryOrderCreateRequest.class);
        } catch (Exception e) {
            response.setFlag(QimenConstants.FAILURE);
            response.setMessage("解析XML出错");
            return response;
        }

        if (request == null) {
            response.setFlag(QimenConstants.FAILURE);
            response.setMessage("请求内容(data)参数格式不正确");
            return  response;
        }



        return response;

    }


}
