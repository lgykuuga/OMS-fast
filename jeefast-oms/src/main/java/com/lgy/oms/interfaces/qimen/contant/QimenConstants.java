package com.lgy.oms.interfaces.qimen.contant;

/**
 * 奇门接口常量信息
 * @author lgy
 */
public class QimenConstants {

    /**
     * 返回成功标识
     */
    public static final String SUCCESS = "success";


    public static final String XML = "XML";

    public static final String JSON = "JSON";

    /**
     * 返回失败标识
     */
    public static final String FAILURE = "failure";

    /**
     * 奇门对接系统:WMS
     */
    public static final String WMS = "wms";

    /**
     * 奇门对接系统:ERP
     */
    public static final String ERP = "erp";

    /**
     * 淘宝奇门路由地址
     */
    public static final String URL = "http://qimen.api.taobao.com/router/qimen/service";

    /**
     * 淘宝方法前缀
     */
    public static final String PREFIX = "taobao.qimen.";

/** ===================================================================================*/

    /**
     * 单据取消接口
     */
    public static final String ORDER_CANCEL = "taobao.qimen.order.cancel";

    /**
     * 单据挂起（恢复）接口
     */
    public static final String ORDER_PENDING = "taobao.qimen.order.pending";

    /**
     * 发货单创建接口
     */
    public static final String DELIVERYORDER_CREATE = "taobao.qimen.deliveryorder.create";

    /**
     * 发货单确认接口
     */
    public static final String DELIVERYORDER_CONFIRM = "taobao.qimen.deliveryorder.confirm";

    /**
     * 发货单创建接口(批量)
     */
    public static final String DELIVERYORDER_BATCHCREATE = "taobao.qimen.deliveryorder.batchcreate";

    /**
     * 发货单确认接口(批量)
     */
    public static final String DELIVERYORDER_BATCHCONFIRM = "taobao.qimen.deliveryorder.batchconfirm";

    /**
     * 入库单创建接口
     */
    public static final String ENTRYORDER_CREATE = "taobao.qimen.entryorder.create";

    /**
     * 入库单确认接口
     */
    public static final String ENTRYORDER_CONFIRM = "taobao.qimen.entryorder.confirm";

    /**
     * 出库单创建接口
     */
    public static final String STOCKOUT_CREATE = "taobao.qimen.stockout.create";

    /**
     * 出库单确认接口
     */
    public static final String STOCKOUT_CONFIRM = "taobao.qimen.stockout.confirm";

    /**
     * 退货入库单创建接口
     */
    public static final String RETURNORDER_CREATE = "taobao.qimen.returnorder.create";

    /**
     * 退货入库单确认接口
     */
    public static final String RETURNORDER_CONFIRM = "taobao.qimen.returnorder.confirm";

    /**
     * 发货单SN通知接口
     */
    public static final String ORDER_SN_REPORT = "taobao.qimen.order.sn.report";

    /**
     * 订单流水通知接口
     */
    public static final String ORDERPROCESS_REPORT = "taobao.qimen.orderprocess.report";

    /**
     * 订单流水查询接口
     */
    public static final String ORDERPROCESS_QUERY = "taobao.qimen.orderprocess.query";

    /**
     * 商品同步接口
     */
    public static final String SINGLEITEM_SYNCHRONIZE = "taobao.qimen.singleitem.synchronize";

    /**
     * 商品同步接口 (批量)
     */
    public static final String ITEMS_SYNCHRONIZE = "taobao.qimen.items.synchronize";

    /**
     * 库存异动通知接口
     */
    public static final String STOCKCHANGE_REPORT = "taobao.qimen.stockchange.report";

    /**
     * 库存查询接口（多商品）
     */
    public static final String INVENTORY_QUERY = "taobao.qimen.inventory.query";


}
