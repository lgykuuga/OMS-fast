package com.lgy.oms.interfaces.qimen.service.oms2erp;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.entryorder.confirm( 入库单确认接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25995&docType=2
 * WMS调用接口，回传入库单信息;
 *
 * @author LGy
 */
public class QimenEntryOrderConfirm extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.ENTRYORDER_CONFIRM;


    @Override
    public CommonResponse<Object> execute() {

        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
