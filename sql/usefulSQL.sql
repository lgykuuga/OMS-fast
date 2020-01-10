/** 清空订单表 */
TRUNCATE table oms_order_buyerinfo;
TRUNCATE table oms_order_detail;
TRUNCATE table oms_order_intercept;
TRUNCATE table oms_order_main;
TRUNCATE table oms_order_payinfo;
TRUNCATE table oms_order_status;
TRUNCATE table oms_order_typeinfo;

/** 删除某个订单 */
DELETE
a,b,c,d,e,f
from oms_order_main a,
oms_order_buyerinfo b,
oms_order_typeinfo c,
oms_order_payinfo d,
oms_order_status e,
oms_order_detail f
where a.order_id = b.order_id
and a.order_id = c.order_id
and a.order_id = d.order_id
and a.order_id = e.order_id
and a.order_id = f.order_id
and a.order_id = 'OD202001070000003'
