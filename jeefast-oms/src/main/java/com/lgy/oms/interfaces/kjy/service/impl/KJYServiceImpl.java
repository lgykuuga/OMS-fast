package com.lgy.oms.interfaces.kjy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.DateUtils;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.enums.PlatformOrderStatusEnum;
import com.lgy.oms.interfaces.kjy.bean.KJYOrderResponse;
import com.lgy.oms.interfaces.kjy.bean.Trades;
import com.lgy.oms.interfaces.kjy.service.IKJYService;
import com.lgy.oms.util.ApacheHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description 请求跨境翼接口实现
 * @Author LGy
 * @Date 2019/10/17 17:08
 **/
@Service
public class KJYServiceImpl implements IKJYService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 请求订单类型
     */
    private static String type = "guarantee_trade,auto_delivery,ec,cod,fenxiao,tmall_i18n";

    /**
     * 请求订单状态
     */
    private static String status = "WAIT_BUYER_PAY,SELLER_CONSIGNED_PART,WAIT_SELLER_SEND_GOODS,TRADE_BUYER_SIGNED,TRADE_FINISHED,TRADE_CLOSED,TRADE_CLOSED_BY_TAOBAO";

    /**
     * 请求订单字段
     */
    private static String fields = "seller_nick,pic_path,payment,seller_rate,post_fee,receiver_name,receiver_state,receiver_address,receiver_zip,receiver_mobile,receiver_phone,consign_time,received_payment,receiver_country,receiver_town,order_tax_fee,shop_pick,tid,\n" +
            "num,num_iid,status,title,type,price,discount_fee,total_fee,created,pay_time,modified,end_time,seller_flag,buyer_nick,has_buyer_message,credit_card_fee,step_trade_status,step_paid_fee,mark_desc,shipping_type,adjust_fee,trade_from,\n" +
            "service_orders,buyer_rate,receiver_city,receiver_district,o2o,o2o_guide_id,o2o_shop_id,o2o_guide_name,o2o_shop_name,o2o_delivery,orders,rx_audit_status,es_range,es_date,os_date,os_range,post_gate_declare,cross_bonded_declare,order_tax_promotion_fee,\n" +
            "service_type,threepl_timing,is_o2o_passport,tmall_delivery,cn_service,cutoff_minutes,es_time,delivery_time,collect_time,dispatch_time,sign_time,delivery_cps";

    @Override
    public CommonResponse<List<Trade>> getOrderFullInfoList(ShopInterfaces shopInterfaces, String bedt, String endt) {

        int page = 1;
        int size = 10;

        Boolean hasNext = true;

        //3定义返回对象
        List<Trade> tradeList = new ArrayList<>();

        while (hasNext) {
            //发送请求
            JSONObject dataObject = sendRequest(shopInterfaces.getSurl(), shopInterfaces.getSecr(),
                    bedt, endt, page, size);

            KJYOrderResponse response = dataObject.toJavaObject(KJYOrderResponse.class);

            if (response.ERROR_CODE.equals(response.getState())) {
                return new CommonResponse<List<Trade>>().error(Constants.FAIL,
                        "调用跨境翼接口返回:" + response.getMsg());
            }

            //跨境翼返回对象
            List<Trades> kjyTrades = response.getTrades();

            for (Trades kjyTrade : kjyTrades) {

                Trade trade = new Trade();
                //平台单号
                trade.setTid(kjyTrade.getTid());
                //平台交易状态
                trade.setStatus(PlatformOrderStatusEnum.switchStatus(kjyTrade.getStatus()));
                //平台更新时间,时间戳格式
                trade.setModified(DateUtils.getDateByTimestamp(kjyTrade.getModified()));
                //设置货主
                trade.setOwner(shopInterfaces.getOwner());
                //设置店铺
                trade.setShop(shopInterfaces.getShop());
                //请求返回消息
                trade.setResponse(JSON.toJSONString(kjyTrade));
                tradeList.add(trade);
            }

            //判断是否存在下一页
            if ("true".equalsIgnoreCase(response.getHas_next())) {
                page++;
            } else {
                hasNext = false;
            }
        }

        if (tradeList.size() > 0) {
            return new CommonResponse<List<Trade>>().ok(tradeList);
        }

        return new CommonResponse<List<Trade>>().error(Constants.FAIL, "请求订单明细为空");

    }

    /**
     * 发送请求
     *
     * @param url           请求地址
     * @param secret        密钥
     * @param start_created 开始时间
     * @param end_created   结束时间
     * @param pageNo        请求页码
     * @param size          页数大小
     * @return
     */
    private JSONObject sendRequest(String url, String secret, String start_created, String end_created,
                                   int pageNo, int size) {

        Map<String, Object> req = new HashMap<>(9);
        req.put("type", type);
        req.put("status", status);
        req.put("start_created", start_created);
        req.put("end_created", end_created);
        req.put("page_no", pageNo);
        req.put("page_size", size);
        req.put("use_has_next", true);
        req.put("fields", fields);
        req.put("App_Session", secret);

        logger.info("请求跨境翼接口入数:{}", JSON.toJSONString(req));
        String result = ApacheHttpUtils.doPost(url, req);
        logger.info("请求跨境翼接返回结果:{}", result);
        return JSON.parseObject(result);
    }


    public static void main(String[] args) {
        String result = "{\"has_next\":\"true\",\"trades\":[{\"adjust_fee\":\"0.00\",\"buyer_nick\":\"符淑妹哈哈\",\"buyer_open_uid\":\"AAGzeanVAB3ZO38gPBzFe0SC\",\"buyer_rate\":\"false\",\"consign_time\":\"2019-08-30 18:27:10\",\"created\":\"2019-08-30 09:56:36\",\"cross_bonded_declare\":\"true\",\"discount_fee\":\"150.00\",\"es_date\":\"2019-09-04\",\"es_range\":\"00:00-23:59\",\"m_tariff_fee\":\"0.00\",\"modified\":\"2019-08-30 18:40:03\",\"new_presell\":\"false\",\"num\":\"1\",\"num_iid\":\"575724034484\",\"order_tax_fee\":\"16.60\",\"order_tax_promotion_fee\":\"150.00\",\"orders\":{\"order\":{\"adjust_fee\":\"0.00\",\"buyer_rate\":\"false\",\"cid\":\"50006010\",\"consign_time\":\"2019-08-30 18:27:10\",\"discount_fee\":\"516.60\",\"divide_order_fee\":\"49.00\",\"invoice_no\":\"1137334394592\",\"is_daixiao\":\"false\",\"is_oversold\":\"false\",\"logistics_company\":\"EMS\",\"num\":\"1\",\"num_iid\":\"575724034484\",\"oid\":\"555594255035371810\",\"oid_str\":\"555594255035371810\",\"order_attr\":\"{\\\"esDate\\\":\\\"2019-09-04\\\",\\\"esRange\\\":\\\"00:00-23:59\\\"}\",\"order_from\":\"WAP,WAP\",\"outer_sku_id\":\"277526104\",\"part_mjz_discount\":\"150.00\",\"payment\":\"49.00\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i3/3827756570/O1CN016XISja1yP6fyKVmvC_!!0-item_pic.jpg\",\"price\":\"699.00\",\"refund_status\":\"NO_REFUND\",\"s_tariff_fee\":\"0.00\",\"seller_rate\":\"false\",\"seller_type\":\"B\",\"shipping_type\":\"express\",\"sku_id\":\"3947293418844\",\"sku_properties_name\":\"颜色分类:肉色;尺码:XL\",\"snapshot_url\":\"p:555594255035371810_1\",\"status\":\"WAIT_BUYER_CONFIRM_GOODS\",\"store_code\":\"STORE_8820233\",\"sub_order_tax_fee\":\"16.60\",\"sub_order_tax_promotion_fee\":\"150.00\",\"sub_order_tax_rate\":\"0\",\"tax_coupon_discount\":\"16.60\",\"tax_free\":\"true\",\"title\":\"Gabrialla产后收腹带顺产剖腹产专用产妇束缚透气无痕塑身衣夏季\",\"total_fee\":\"182.40\"}},\"pay_time\":\"2019-08-30 09:56:40\",\"payment\":\"49.00\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i3/3827756570/O1CN016XISja1yP6fyKVmvC_!!0-item_pic.jpg\",\"post_fee\":\"0.00\",\"post_gate_declare\":\"false\",\"price\":\"699.00\",\"received_payment\":\"0.00\",\"receiver_address\":\"大同街道海口丽都花园A栋309\",\"receiver_city\":\"海口市\",\"receiver_country\":\"\",\"receiver_district\":\"龙华区\",\"receiver_mobile\":\"13907543482\",\"receiver_name\":\"符女士\",\"receiver_state\":\"海南省\",\"receiver_town\":\"大同街道\",\"receiver_zip\":\"570105\",\"seller_flag\":\"0\",\"seller_nick\":\"gabrialla海外旗舰店\",\"seller_rate\":\"false\",\"service_type\":\"\",\"shipping_type\":\"express\",\"status\":\"WAIT_BUYER_CONFIRM_GOODS\",\"tid\":\"555594255035371810\",\"tid_str\":\"555594255035371810\",\"title\":\"gabrialla海外旗舰店\",\"total_fee\":\"715.60\",\"trade_from\":\"WAP,WAP\",\"type\":\"tmall_i18n\",\"you_xiang\":\"false\"},{\"adjust_fee\":\"0.00\",\"buyer_nick\":\"easybu9303\",\"buyer_open_uid\":\"AAHTeanVAB3ZO38gPBxtjkPo\",\"buyer_rate\":\"false\",\"consign_time\":\"2019-08-30 18:27:53\",\"created\":\"2019-08-30 09:37:05\",\"cross_bonded_declare\":\"true\",\"discount_fee\":\"0.00\",\"end_time\":\"2019-09-09 15:53:44\",\"es_date\":\"2019-09-03\",\"es_range\":\"00:00-23:59\",\"m_tariff_fee\":\"0.00\",\"modified\":\"2019-09-09 15:54:37\",\"new_presell\":\"false\",\"num\":\"1\",\"num_iid\":\"568200887292\",\"order_tax_fee\":\"8.26\",\"order_tax_promotion_fee\":\"0.00\",\"orders\":{\"order\":{\"adjust_fee\":\"0.00\",\"buyer_rate\":\"false\",\"cid\":\"50012363\",\"consign_time\":\"2019-08-30 18:27:53\",\"discount_fee\":\"368.26\",\"divide_order_fee\":\"99.00\",\"end_time\":\"2019-09-09 15:53:44\",\"invoice_no\":\"1137320636692\",\"is_daixiao\":\"false\",\"is_oversold\":\"false\",\"logistics_company\":\"EMS\",\"num\":\"1\",\"num_iid\":\"568200887292\",\"oid\":\"600033571706788874\",\"oid_str\":\"600033571706788874\",\"order_attr\":\"{\\\"esDate\\\":\\\"2019-09-03\\\",\\\"esRange\\\":\\\"00:00-23:59\\\"}\",\"order_from\":\"WAP,WAP\",\"outer_sku_id\":\"259233706\",\"payment\":\"99.00\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i1/3827756570/O1CN01MYHo0K1yP6hTp1Q07_!!0-item_pic.jpg\",\"price\":\"459.00\",\"refund_id\":\"35933376827787488\",\"refund_status\":\"SUCCESS\",\"s_tariff_fee\":\"0.00\",\"seller_rate\":\"false\",\"seller_type\":\"B\",\"shipping_type\":\"express\",\"sku_id\":\"3627689829282\",\"sku_properties_name\":\"颜色分类:黑色S码（升级款）\",\"snapshot_url\":\"p:600033571706788874_1\",\"status\":\"TRADE_CLOSED\",\"store_code\":\"STORE_8820233\",\"sub_order_tax_fee\":\"8.26\",\"sub_order_tax_promotion_fee\":\"0.00\",\"sub_order_tax_rate\":\"0\",\"tax_coupon_discount\":\"8.26\",\"tax_free\":\"true\",\"title\":\"Gabrialla托腹带孕期女孕妇专用秋夏季透气怀孕晚期拖腹带护腰带\",\"total_fee\":\"90.74\"}},\"pay_time\":\"2019-08-30 09:37:10\",\"payment\":\"99.00\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i1/3827756570/O1CN01MYHo0K1yP6hTp1Q07_!!0-item_pic.jpg\",\"post_fee\":\"0.00\",\"post_gate_declare\":\"false\",\"price\":\"459.00\",\"received_payment\":\"0.00\",\"receiver_address\":\"合肥路街道美寓天城南区6号楼2单元801（不要放云柜，放云柜直接拒收）\",\"receiver_city\":\"青岛市\",\"receiver_country\":\"\",\"receiver_district\":\"市北区\",\"receiver_mobile\":\"15588983389\",\"receiver_name\":\"周佳丽\",\"receiver_state\":\"山东省\",\"receiver_town\":\"合肥路街道\",\"receiver_zip\":\"000000\",\"seller_flag\":\"2\",\"seller_nick\":\"gabrialla海外旗舰店\",\"seller_rate\":\"false\",\"service_type\":\"\",\"shipping_type\":\"express\",\"status\":\"TRADE_CLOSED\",\"tid\":\"600033571706788874\",\"tid_str\":\"600033571706788874\",\"title\":\"gabrialla海外旗舰店\",\"total_fee\":\"467.26\",\"trade_from\":\"WAP,WAP\",\"type\":\"tmall_i18n\",\"you_xiang\":\"false\"},{\"adjust_fee\":\"0.00\",\"buyer_nick\":\"biheshiwo\",\"buyer_open_uid\":\"AAHreanVAB3ZO38gPBxUi_zj\",\"buyer_rate\":\"true\",\"consign_time\":\"2019-08-30 18:10:24\",\"created\":\"2019-08-30 06:26:18\",\"cross_bonded_declare\":\"true\",\"discount_fee\":\"150.00\",\"end_time\":\"2019-09-03 22:40:13\",\"es_date\":\"2019-09-03\",\"es_range\":\"00:00-23:59\",\"m_tariff_fee\":\"0.00\",\"modified\":\"2019-09-03 22:40:55\",\"new_presell\":\"false\",\"num\":\"1\",\"num_iid\":\"567665665677\",\"order_tax_fee\":\"16.60\",\"order_tax_promotion_fee\":\"150.00\",\"orders\":{\"order\":{\"adjust_fee\":\"0.00\",\"buyer_rate\":\"true\",\"cid\":\"50006010\",\"consign_time\":\"2019-08-30 18:10:24\",\"discount_fee\":\"216.60\",\"divide_order_fee\":\"49.00\",\"end_time\":\"2019-09-03 22:40:13\",\"fqg_num\":\"3\",\"invoice_no\":\"YT4056543008435\",\"is_daixiao\":\"false\",\"is_fqg_s_fee\":\"false\",\"is_oversold\":\"false\",\"logistics_company\":\"圆通速递\",\"num\":\"1\",\"num_iid\":\"567665665677\",\"oid\":\"555520014794701507\",\"oid_str\":\"555520014794701507\",\"order_attr\":\"{\\\"esDate\\\":\\\"2019-09-03\\\",\\\"esRange\\\":\\\"00:00-23:59\\\"}\",\"order_from\":\"WAP,WAP\",\"outer_sku_id\":\"259847203\",\"part_mjz_discount\":\"150.00\",\"payment\":\"49.00\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i2/3827756570/O1CN01gCNVqr1yP6g7F0bzS_!!0-item_pic.jpg\",\"price\":\"399.00\",\"refund_status\":\"NO_REFUND\",\"s_tariff_fee\":\"0.00\",\"seller_rate\":\"true\",\"seller_type\":\"B\",\"shipping_type\":\"express\",\"sku_id\":\"3786809951034\",\"sku_properties_name\":\"颜色分类:白色;尺码:L\",\"snapshot_url\":\"p:555520014794701507_1\",\"status\":\"TRADE_FINISHED\",\"store_code\":\"STORE_8820233\",\"sub_order_tax_fee\":\"16.60\",\"sub_order_tax_promotion_fee\":\"150.00\",\"sub_order_tax_rate\":\"0\",\"tax_coupon_discount\":\"16.60\",\"tax_free\":\"true\",\"title\":\"Gabrialla产后收腹带顺产剖腹产专用产妇束缚透气无痕塑身衣夏季\",\"total_fee\":\"182.40\"}},\"pay_time\":\"2019-08-30 06:26:25\",\"payment\":\"49.00\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i2/3827756570/O1CN01gCNVqr1yP6g7F0bzS_!!0-item_pic.jpg\",\"post_fee\":\"0.00\",\"post_gate_declare\":\"false\",\"price\":\"399.00\",\"received_payment\":\"49.00\",\"receiver_address\":\"宝平街道开元路33号\",\"receiver_city\":\"天津市\",\"receiver_country\":\"\",\"receiver_district\":\"宝坻区\",\"receiver_mobile\":\"15222721987\",\"receiver_name\":\"王瑾\",\"receiver_state\":\"天津\",\"receiver_town\":\"宝平街道\",\"receiver_zip\":\"301800\",\"seller_flag\":\"0\",\"seller_nick\":\"gabrialla海外旗舰店\",\"seller_rate\":\"true\",\"service_type\":\"\",\"shipping_type\":\"express\",\"status\":\"TRADE_FINISHED\",\"tid\":\"555520014794701507\",\"tid_str\":\"555520014794701507\",\"title\":\"gabrialla海外旗舰店\",\"total_fee\":\"415.60\",\"trade_from\":\"WAP,WAP\",\"type\":\"tmall_i18n\",\"you_xiang\":\"false\"}]}";
        JSONObject jsonObject = JSON.parseObject(result);
        KJYOrderResponse response = jsonObject.toJavaObject(KJYOrderResponse.class);
        System.out.println(response);
    }
}
