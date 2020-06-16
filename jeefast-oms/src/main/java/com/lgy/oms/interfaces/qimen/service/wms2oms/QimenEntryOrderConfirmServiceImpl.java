package com.lgy.oms.interfaces.qimen.service.wms2oms;


import com.alibaba.fastjson.JSON;
import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenParam;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.entryorder.EntryOrderConfirmRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * taobao.qimen.entryorder.confirm( 入库单确认接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25995&docType=2
 * WMS调用接口，回传入库单信息;
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.ENTRYORDER_CONFIRM)
public class QimenEntryOrderConfirmServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(QimenParam qimenParam) {

        QimenResponse response = new QimenResponse();
        EntryOrderConfirmRequest request = null;

        /** 1. XML转换 检查参数 */
        try {
            if (QimenConstants.XML.equalsIgnoreCase(qimenParam.getFormat())) {
                request = JaxbUtil.converyToJavaBean(qimenParam.getData(), EntryOrderConfirmRequest.class);
            } else {
                request = JSON.parseObject(qimenParam.getData(), EntryOrderConfirmRequest.class);
            }
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
