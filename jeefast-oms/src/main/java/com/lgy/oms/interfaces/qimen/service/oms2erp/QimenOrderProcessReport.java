package com.lgy.oms.interfaces.qimen.service.oms2erp;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.orderprocess.report( 订单流水通知接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=26003&docType=2
 * WMS调用奇门的接口,将订单在仓库的状态回传给ERP；场景说明：仓库仓内操作状态回传给ERP,
 * 比如打包操作完成时, 回传一个打 包完成的状态给到ERP, ERP自行决定如何处理。
 *
 * @author LGy
 */
public class QimenOrderProcessReport extends QimenAbstractBaseSingleInterfaceImpl implements
        QimenSingleInterface {

    private final String method = QimenConstants.ORDERPROCESS_REPORT;


    @Override
    public CommonResponse<Object> execute() {

        return null;
    }

    @Override
    public void dataConvert(Object data) {


    }


}
