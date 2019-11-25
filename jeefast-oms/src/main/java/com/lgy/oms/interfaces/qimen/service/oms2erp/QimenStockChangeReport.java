package com.lgy.oms.interfaces.qimen.service.oms2erp;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.stockchange.report( 库存异动通知接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=25996&docType=2
 * WMS调用奇门的接口,将库存异动信息信息回传给ERP
 *
 * @author LGy
 */
public class QimenStockChangeReport extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.STOCKCHANGE_REPORT;


    @Override
    public CommonResponse<Object> execute() {

        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
