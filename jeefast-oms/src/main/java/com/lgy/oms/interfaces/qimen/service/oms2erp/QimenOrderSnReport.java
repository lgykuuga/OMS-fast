package com.lgy.oms.interfaces.qimen.service.oms2erp;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenAbstractBaseSingleInterfaceImpl;
import com.lgy.oms.interfaces.qimen.service.QimenSingleInterface;


/**
 * taobao.qimen.order.sn.report( 订单SN通知接口 )
 * https://open.taobao.com/api.htm?cid=20725&docId=27962&docType=2
 * WMS调用奇门的接口,在出库、发货、入库等场景下，ERP和WMS之间同步操作的SN列表
 *
 * @author LGy
 */
public class QimenOrderSnReport extends QimenAbstractBaseSingleInterfaceImpl implements
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
