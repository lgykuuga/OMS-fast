package com.lgy.oms.interfaces.qimen.service.oms2wms;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.items.synchronize( 商品同步接口 (批量) )
 * https://open.taobao.com/api.htm?cid=20725&docId=27262&docType=2
 * ERP调用奇门的接口,批量同步商品信息给WMS
 *
 * @author LGy
 */
public class QimenItemsSynchronize extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.ITEMS_SYNCHRONIZE;


    @Override
    public CommonResponse<Object> execute() {


        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
