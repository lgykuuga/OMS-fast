package com.lgy.oms.interfaces.qimen.service.erp2oms;


import com.alibaba.fastjson.JSON;
import com.lgy.common.utils.xml.JaxbUtil;
import com.lgy.oms.interfaces.qimen.bean.QimenParam;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.bean.order.OrderCancelRequest;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 奇门单据取消接口  taobao.qimen.order.cancel
 * http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.aO69uk&apiId=26000
 * ERP调用奇门的接口,取消创建单据操作。场景介绍：ERP主动发起取消某些创建的单据。如入库单、出库单、退货单等；所有的场景
 *
 * @Author LGy
 * @Date 2019/11/22
 */

@Service(QimenConstants.ORDER_CANCEL)
public class QimenOrderCancelServiceImpl implements QimenService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public QimenResponse requestExec(QimenParam qimenParam) {
        QimenResponse response = new QimenResponse();
        OrderCancelRequest request;

        /** 1. XML转换 检查参数 */
        try {
            if (QimenConstants.XML.equalsIgnoreCase(qimenParam.getFormat())) {
                request = JaxbUtil.converyToJavaBean(qimenParam.getData(), OrderCancelRequest.class);
            } else {
                request = JSON.parseObject(qimenParam.getData(), OrderCancelRequest.class);
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
