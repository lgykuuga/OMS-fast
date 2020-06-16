package com.lgy.oms.interfaces.qimen.service.erp2oms;


import com.alibaba.fastjson.JSON;
import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenParam;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.deliveryorder.create.DeliveryorderBatchCreateRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * taobao.qimen.deliveryorder.batchcreate( 发货单创建批量接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25994&docType=2
 * ERP调用接口，将发货信息批量推送给WMS
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.DELIVERYORDER_BATCHCREATE)
public class QimenDeliveryOrderBatchCreateServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(QimenParam qimenParam) {

        QimenResponse response = new QimenResponse();
        DeliveryorderBatchCreateRequest request;

        /** 1. 转换 检查参数 */
        try {
            if (QimenConstants.XML.equalsIgnoreCase(qimenParam.getFormat())) {
                request = JaxbUtil.converyToJavaBean(qimenParam.getData(), DeliveryorderBatchCreateRequest.class);
            } else {
                request = JSON.parseObject(qimenParam.getData(), DeliveryorderBatchCreateRequest.class);
            }
        } catch (Exception e) {
            response.setFlag(QimenConstants.FAILURE);
            response.setMessage("解析" + qimenParam.getFormat() + "出错");
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
