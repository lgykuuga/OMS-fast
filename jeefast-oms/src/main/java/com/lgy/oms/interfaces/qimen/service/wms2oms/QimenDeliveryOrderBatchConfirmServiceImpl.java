package com.lgy.oms.interfaces.qimen.service.wms2oms;


import com.alibaba.fastjson.JSON;
import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenParam;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm.DeliveryOrderBatchConfirmRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * taobao.qimen.deliveryorder.batchconfirm( 发货单确认接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25993&docType=2
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.DELIVERYORDER_BATCHCONFIRM)
public class QimenDeliveryOrderBatchConfirmServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(QimenParam qimenParam) {

        QimenResponse response = new QimenResponse();
        DeliveryOrderBatchConfirmRequest request;

        /** 1. 转换 检查参数 */
        try {
            if (QimenConstants.XML.equalsIgnoreCase(qimenParam.getFormat())) {
                request = JaxbUtil.converyToJavaBean(qimenParam.getData(), DeliveryOrderBatchConfirmRequest.class);
            } else {
                request = JSON.parseObject(qimenParam.getData(), DeliveryOrderBatchConfirmRequest.class);
            }
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
