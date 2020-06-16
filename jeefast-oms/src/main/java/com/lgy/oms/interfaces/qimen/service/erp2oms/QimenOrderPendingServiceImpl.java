package com.lgy.oms.interfaces.qimen.service.erp2oms;


import com.alibaba.fastjson.JSON;
import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenParam;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.order.OrderPendingRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.springframework.stereotype.Service;


/**
 * taobao.qimen.order.pending( 单据挂起（恢复）接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=27276&docType=2
 * ERP调用奇门的接口,挂起某些创建的单据;场景介绍：ERP主动发起挂起（恢复）某些创建的单据，如入库单、出库单、退货单等
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.ORDER_PENDING)
public class QimenOrderPendingServiceImpl implements QimenService {


    @Override
    public QimenResponse requestExec(QimenParam qimenParam) {

        QimenResponse response = new QimenResponse();
        OrderPendingRequest request;

        /** 1. XML转换 检查参数 */
        try {
            if (QimenConstants.XML.equalsIgnoreCase(qimenParam.getFormat())) {
                request = JaxbUtil.converyToJavaBean(qimenParam.getData(), OrderPendingRequest.class);
            } else {
                request = JSON.parseObject(qimenParam.getData(), OrderPendingRequest.class);
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
