package com.lgy.oms.interfaces.qimen.service.oms2wms;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.deliveryorder.batchcreate( 发货单创建批量接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25994&docType=2
 * ERP调用接口，将发货信息批量推送给WMS
 *
 * @author LGy
 */
public class QimenDeliveryOrderBatchCreate extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.DELIVERYORDER_BATCHCREATE;


    @Override
    public CommonResponse<Object> execute() {


        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
