package com.lgy.oms.interfaces.qimen.service.oms2erp;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.returnorder.confirm( 退货入库单确认接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25997&docType=2
 *
 * @author LGy
 */
public class QimenReturnOrderConfirm extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.RETURNORDER_CONFIRM;


    @Override
    public CommonResponse<Object> execute() {

        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
