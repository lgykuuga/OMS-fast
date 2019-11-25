package com.lgy.oms.interfaces.qimen.service.oms2wms;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.singleitem.synchronize( 商品同步接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25998&docType=2
 * ERP调用奇门的接口,同步商品信息给WMS
 *
 * @author LGy
 */
public class QimenSingleItemSynchronize extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.SINGLEITEM_SYNCHRONIZE;


    @Override
    public CommonResponse<Object> execute() {


        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
