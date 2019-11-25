package com.lgy.oms.interfaces.qimen.service.oms2wms;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.entryorder.create( 入库单创建接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25991&docType=2
 * ERP调用接口，创建入库单;
 *
 * @author LGy
 */
public class QimenEntryOrderCreate extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.ENTRYORDER_CREATE;


    @Override
    public CommonResponse<Object> execute() {


        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
