package com.lgy.oms.interfaces.qimen.service.oms2wms;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.order.cancel( 单据取消接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=26000&docType=2
 * ERP调用奇门的接口,取消创建单据操作。场景介绍：ERP主动发起取消某些创建的单据。如入库单、出库单、退货单等；所有的场景
 *
 * @author LGy
 */
public class QimenOrderCancel extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.ORDER_CANCEL;


    @Override
    public CommonResponse<Object> execute() {


        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
