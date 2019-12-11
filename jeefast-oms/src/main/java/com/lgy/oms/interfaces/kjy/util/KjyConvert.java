package com.lgy.oms.interfaces.kjy.util;

import com.lgy.common.utils.DateUtils;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrderDetail;
import com.lgy.oms.interfaces.kjy.bean.KjyTrade;
import com.lgy.oms.interfaces.kjy.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 跨境翼下载对象转换成标准订单对象
 * @Author LGy
 * @Date 2019/12/2 15:22
 **/
public class KjyConvert {

    public static StandardOrder changeStandard(KjyTrade kjyTrade, ShopInterfaces shopInterfaces) {
        //标准订单
        StandardOrder standardOrder = new StandardOrder();

        //交易编号 (父订单的交易编号)
        standardOrder.setTid(kjyTrade.getTid());
        //店铺编码
        standardOrder.setShop(shopInterfaces.getShop());
        //平台编码
        standardOrder.setPlatform(shopInterfaces.getPlatform());
        //货主编码
        standardOrder.setOwner(shopInterfaces.getOwner());
        //卖家昵称
        standardOrder.setSeller_nick(kjyTrade.getSeller_nick());
        //实付金额
        standardOrder.setPayment(kjyTrade.getPayment());
        //邮费
        standardOrder.setPost_fee(kjyTrade.getPost_fee());
        //收货人的姓名
        standardOrder.setReceiver_name(kjyTrade.getReceiver_name());
        //收货人的所在省份
        standardOrder.setReceiver_state(kjyTrade.getReceiver_state());
        //收货人的详细地址
        standardOrder.setReceiver_address(kjyTrade.getReceiver_address());
        //收货人的邮编
        standardOrder.setReceiver_zip(kjyTrade.getReceiver_zip());
        //收货人的手机号码
        standardOrder.setReceiver_mobile(kjyTrade.getReceiver_mobile());
        //商家的预计发货时间
        standardOrder.setEst_con_time("");
        //收货人国籍
        standardOrder.setReceiver_country(kjyTrade.getReceiver_country());
        //收货人街道地址
        standardOrder.setReceiver_town(kjyTrade.getReceiver_town());
        //税费
        standardOrder.setOrder_tax_fee(kjyTrade.getOrder_tax_fee());
        //交易状态
        standardOrder.setStatus(kjyTrade.getStatus());
        //店铺
        standardOrder.setStatus(kjyTrade.getStatus());
        //商品价格
        standardOrder.setPrice(kjyTrade.getPrice());
        //优惠金额
        standardOrder.setDiscount_fee(kjyTrade.getDiscount_fee());
        //是否包含邮费。与available_confirm_fee同时使用。可选值:true(包含),false(不包含)
        standardOrder.setHas_post_fee("");
        //商品金额（商品价格乘以数量的总金额）
        standardOrder.setTotal_fee(kjyTrade.getTotal_fee());
        //交易创建时间
        standardOrder.setCreated(DateUtils.longToString(kjyTrade.getCreated()));
        //付款时间
        standardOrder.setPay_time(DateUtils.longToString(kjyTrade.getPay_time()));
        //交易修改时间
        standardOrder.setModified(DateUtils.longToString(kjyTrade.getModified()));
        //交易结束时间
        standardOrder.setEnd_time("");
        //买家留言
        standardOrder.setBuyer_message("");
        //卖家备注
        standardOrder.setSeller_memo("");
        //卖家备注旗帜
        standardOrder.setSeller_flag(kjyTrade.getSeller_flag());
        //买家昵称
        standardOrder.setBuyer_nick(kjyTrade.getBuyer_nick());
        //卖家手工调整金额
        standardOrder.setAdjust_fee(kjyTrade.getAdjust_fee());
        //收货人的所在城市
        standardOrder.setReceiver_city(kjyTrade.getReceiver_city());
        //收货人的所在地区
        standardOrder.setReceiver_district(kjyTrade.getReceiver_district());

        //订单明细信息
        List<Order> orders = kjyTrade.getOrders();
        if (orders != null && !orders.isEmpty()) {

            List<StandardOrderDetail> details = new ArrayList<>(orders.size());

            for (Order order : orders) {
                StandardOrderDetail detail = new StandardOrderDetail();
                //订单快照URL
                detail.setSnapshot_url(order.getSnapshot_url());
                //子订单编号
                detail.setOid(order.getOid());
                //商品标题
                detail.setTitle(order.getTitle());
                //商品图片的绝对路径
                detail.setPic_path(order.getPic_path());
                //退款状态
                detail.setRefund_status(order.getRefund_status());
                //订单状态
                detail.setStatus(order.getStatus());
                //购买数量
                detail.setNum(order.getNum());
                //商品数字ID
                detail.setNum_iid(order.getNum_iid());
                //商家外部编码
                detail.setOuter_iid(order.getOuter_sku_id());
                //商品的最小库存单位Sku的id
                detail.setSku_id(order.getSku_id());
                //外部网店自己定义的Sku编号
                detail.setOuter_sku_id(order.getOuter_sku_id());
                //订单超时到期时间
                detail.setTimeout_action_time("");
                //交易商品对应的类目ID
                detail.setCid(order.getCid());
                //商品价格
                detail.setPrice(order.getPrice());
                //应付金额
                detail.setTotal_fee(order.getTotal_fee());
                //子订单实付金额
                detail.setPayment(order.getPayment());
                //子订单级订单优惠金额
                detail.setDiscount_fee(order.getDiscount_fee());
                //手工调整金额
                detail.setAdjust_fee(order.getAdjust_fee());
                //分摊之后的实付金额
                detail.setDivide_order_fee(order.getDivide_order_fee());
                //优惠分摊
                detail.setPart_mjz_discount(order.getPart_mjz_discount());
                //子订单发货的快递公司名称
                detail.setLogistics_company(order.getLogistics_company());
                //子订单所在包裹的运单号
                detail.setInvoice_no(order.getInvoice_no());
                //发货的仓库编码
                detail.setStore_code(order.getStore_code());
                //是否发货
                detail.setIs_sh_ship("");
                details.add(detail);
            }

            standardOrder.setOrderDetails(details);
        }


        return standardOrder;
    }
}
