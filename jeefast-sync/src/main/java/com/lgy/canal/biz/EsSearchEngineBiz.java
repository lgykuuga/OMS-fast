package com.lgy.canal.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.lgy.base.domain.Owner;
import com.lgy.base.domain.Platform;
import com.lgy.base.domain.Shop;
import com.lgy.base.service.IOwnerService;
import com.lgy.base.service.IPlatformService;
import com.lgy.base.service.IShopService;
import com.lgy.biz.order.OrderEsSearchEngine;
import com.lgy.canal.constant.EsOrderTableConstant;
import com.lgy.canal.model.OrderMainMessage;
import com.lgy.constant.EsOrderFieldConstant;
import com.lgy.es.EsSearchEngine;
import com.lgy.es.util.EsUpdateBuildUtils;
import com.lgy.model.order.EsOrderDetail;
import com.lgy.model.order.EsOrderMain;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/25
 */
@Service
@Log4j2
public class EsSearchEngineBiz extends EsSearchEngine {

    private static final String ERROR_ATTR = "error";

    @Resource
    OrderEsSearchEngine orderEsSearchEngine;

    @Autowired
    IOwnerService ownerService;
    @Autowired
    IPlatformService platformService;
    @Autowired
    IShopService shopService;


    public String insert(Map data) {

        if (MapUtils.isEmpty(data)) {
            return null;
        }

        String orderId = (String) data.get(EsOrderFieldConstant.ORDER_ID);

        log.info("请求写入订单{}数据 -> {}", orderId, data);

        try {
            JSONObject jsonObject = new JSONObject(data);
            orderEsSearchEngine.addData(jsonObject, orderId);
        } catch (Exception e) {
            log.error("添加ES异常, orderId->{}", orderId, e);
            return orderId;
        }

        log.info("订单写入成功-> {}", orderId);

        return orderId;
    }

    /**
     * 批量插入
     */
    public void batchInsert(List<Map> orders) {

        StringBuilder bulkRequestBody = new StringBuilder();

        for (Map order : orders) {
            String actionMetaData = String.format("{ \"index\" : {\"_id\" : \"%s\"} }%n", order.get(EsOrderFieldConstant.ORDER_ID));
            String orderJson = JSON.toJSONString(order, SerializerFeature.WriteNullStringAsEmpty);
            bulkRequestBody.append(actionMetaData);
            bulkRequestBody.append(orderJson);
            bulkRequestBody.append("\n");
        }

        orderEsSearchEngine.bulkInsert(orders);
    }

    public void batchUpsert(Map<String, String> map) {

        log.info("批量更新数据-> {}", map);

        String orderId = map.get(EsOrderFieldConstant.ORDER_ID);

        JSONObject jsonObject = new JSONObject((Map) map);

        orderEsSearchEngine.updateDataById(jsonObject, orderId);
    }

    private String getUpsertDoc(String innerTableName, EsOrderMain order) {

        boolean isNested = EsOrderTableConstant.OMS_ORDER_DETAIL.equals(innerTableName);

        return EsUpdateBuildUtils.buildUpsert(order, isNested);
    }

    private void delete(String orderId) {
        log.info("请求删除文档 -> {}", orderId);
        try {
            orderEsSearchEngine.deleteDataById(orderId);
        } catch (Exception e) {
            log.error("删除文档异常,orderId->{}", orderId, e);
        }
    }

    public Map<String, Object> getOrderById(String orderId) {

        Map<String, Object> result = Maps.newHashMap();

        try {
            result = orderEsSearchEngine.searchDataById(orderId, null);
        } catch (Exception e) {
            log.error(e);
        }

        if (result == null) {
            return Maps.newHashMap();
        }

        List esOrders = (List) result.get("hits");

        if (CollectionUtils.isEmpty(esOrders)) {
            return Maps.newHashMap();
        }

        Map doc = (Map) esOrders.get(0);

        return Optional.ofNullable((Map) doc.get(EsOrderFieldConstant.ES_SOURCE)).orElse(Maps.newHashMap());
    }


    public void delete(Map<String, Object> modifyData, EsOrderMain order, String innerTableName) {

        if (EsOrderTableConstant.OMS_ORDER_MAIN.equals(innerTableName)) {
            delete(String.valueOf(order.getOrderId()));
            return;
        }

        if (EsOrderTableConstant.OMS_ORDER_PAYINFO.equals(innerTableName)) {
            order.setOrderPayment(null);

        } else if (EsOrderTableConstant.OMS_ORDER_DETAIL.equals(innerTableName)) {
            List<EsOrderDetail> orderItems = extractOrderItems(modifyData, order);
            order.setOrderDetails(orderItems);

        } else if (EsOrderTableConstant.OMS_ORDER_BUYERINFO.equals(innerTableName)) {
            order.setOrderBuyer(null);

        } else if (EsOrderTableConstant.OMS_ORDER_STATUS.equals(innerTableName)) {
            order.setOrderStatus(null);

        } else if (EsOrderTableConstant.OMS_ORDER_TYPEINFO.equals(innerTableName)) {
            order.setOrderType(null);
        }

        insert((Map) JSON.toJSON(order));
    }


    /**
     * 针对删除，抽取出删除后的order_item（nested类型）
     */
    private List<EsOrderDetail> extractOrderItems(Map<String, Object> modifyData, EsOrderMain order) {

        List<EsOrderDetail> esOrderDetails = order.getOrderDetails();
        EsOrderDetail esOrderItem = JSONObject.toJavaObject(new JSONObject(modifyData), EsOrderDetail.class);

        boolean isExit = false;

        if (CollectionUtils.isNotEmpty(esOrderDetails)) {
            for (int i = 0; i < esOrderDetails.size(); i++) {
                EsOrderDetail esOrderDetail = esOrderDetails.get(i);
                if (StringUtils.equals(String.valueOf(esOrderDetail.getOrderId()), String.valueOf(esOrderItem.getOrderId()))) {
                    esOrderDetails.set(i, esOrderItem);
                    esOrderDetails.remove(i);
                    isExit = true;
                }
            }
        }

        if (!isExit) {
            esOrderDetails.add(esOrderItem);
        }

        return esOrderDetails;
    }


    public String getOrderMainDoc(OrderMainMessage orderMainMessage) {

        return getUpsertDoc(orderMainMessage.getTableName(), orderMainMessage.getOrderMain());
    }

    private Map getErrorMsg(Map p) {
        Map<String, Object> errorMsg = Maps.newHashMap();
        errorMsg.put("_id", p.get("_id"));
        errorMsg.put(ERROR_ATTR, p.get(ERROR_ATTR));
        return errorMsg;
    }


    public Map<String, Owner> listByOwnerGcoToMap(List<String> owners) {
        if (CollectionUtils.isEmpty(owners)) {
            return Maps.newHashMap();
        }
        List<Owner> ownerList = ownerService.listByGcos(owners);
        if (CollectionUtils.isEmpty(ownerList)) {
            return Maps.newHashMap();
        }
        return ownerList.stream().collect(Collectors.toMap(Owner::getGco, Function.identity()));
    }

    public Map<String, Platform> listByPlatformGcoToMap(List<String> platforms) {
        if (CollectionUtils.isEmpty(platforms)) {
            return Maps.newHashMap();
        }
        List<Platform> platformList = platformService.listByGcos(platforms);
        if (CollectionUtils.isEmpty(platformList)) {
            return Maps.newHashMap();
        }
        return platformList.stream().collect(Collectors.toMap(Platform::getGco, Function.identity()));
    }

    public Map<String, Shop> listByShopGcoToMap(List<String> shops) {
        if (CollectionUtils.isEmpty(shops)) {
            return Maps.newHashMap();
        }
        List<Shop> shopList = shopService.listByGcos(shops);
        if (CollectionUtils.isEmpty(shopList)) {
            return Maps.newHashMap();
        }
        return shopList.stream().collect(Collectors.toMap(Shop::getGco, Function.identity()));
    }

}
