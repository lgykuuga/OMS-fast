package com.lgy.oms.interfaces.qimen.service.oms2wms;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.stockout.create( 出库单创建接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25992&docType=2
 * ERP调用奇门接口，创建出库单信息
 *
 * @author LGy
 */
public class QimenStockoutCreate extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.STOCKOUT_CREATE;


    @Override
    public CommonResponse<Object> execute() {


        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
