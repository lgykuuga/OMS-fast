package com.lgy.oms.interfaces.qimen.service.oms2erp;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.deliveryorder.batchconfirm( 发货单确认接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25993&docType=2
 *
 * @author LGy
 */
public class QimenDeliveryOrderBatchConfirm extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.DELIVERYORDER_BATCHCONFIRM;


    @Override
    public CommonResponse<Object> execute() {

        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
