package com.lgy.canal.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lgy.base.domain.Owner;
import com.lgy.base.domain.Platform;
import com.lgy.base.domain.Shop;
import com.lgy.base.service.IOwnerService;
import com.lgy.base.service.IPlatformService;
import com.lgy.base.service.IShopService;
import com.lgy.canal.constant.EsOrderFieldConstant;
import com.lgy.canal.constant.EsOrderTableConstant;
import com.lgy.canal.model.OrderMainMessage;
import com.lgy.canal.util.EsFieldUtils;
import com.lgy.es.EsSearchEngine;
import com.lgy.es.exception.EsException;
import com.lgy.es.param.EsSort;
import com.lgy.es.util.EsUpdateBuildUtils;
import com.lgy.model.distribution.EsDistributionDetail;
import com.lgy.model.distribution.EsDistributionOrder;
import com.lgy.model.order.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
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

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private static final String ES_TYPE = "order_main";
    private static final String BASIC_FORMAT = "/%s/%s/%s";
    private static final String ERROR_ATTR = "error";
    private static final int BATCH_SIZE = 5000;

    @Resource
    TransportClient transportClient;

    @Autowired
    IOwnerService ownerService;
    @Autowired
    IPlatformService platformService;
    @Autowired
    IShopService shopService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${elasticsearch.index}")
    private String index;

    public String insert(Map data) {

        if (MapUtils.isEmpty(data)) {
            return null;
        }

        String orderId = (String) data.get(EsOrderFieldConstant.ORDER_ID);

        String postJson = JSON.toJSONString(data, SerializerFeature.WriteNullStringAsEmpty);

        //格式: oms/order/OD20200426000001
        String router = String.format(BASIC_FORMAT, index, ES_TYPE, orderId);

        log.info("请求路径 -> {}, 请求数据 -> {}", router, postJson);

        try {
            transportClient.prepareIndex(index, ES_TYPE, orderId).setSource(data).get();
        } catch (Exception e) {
            log.error("添加ES异常, orderId->{}", orderId, e);
            return orderId;
        }

        log.info("订单同步成功-> {}", orderId);

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

        bulk(bulkRequestBody, "index");
    }

    public void batchUpsert(Map<String, String> jsons) {

        StringBuilder bulkRequestBody = new StringBuilder();

        log.info("批量更新数据-> {}", jsons);

        for (Map.Entry<String, String> entry : jsons.entrySet()) {
            String id = Arrays.asList(entry.getKey().split("_")).get(0);
            String actionMetaData = String.format("{ \"update\" : {\"_id\" : \"%s\", \"_type\" : \"%s\", \"_index\" : \"%s\", \"_retry_on_conflict\" : %d} }%n", id, ES_TYPE, index, 10);
            bulkRequestBody.append(actionMetaData);
            bulkRequestBody.append(entry.getValue());
            bulkRequestBody.append("\n");
        }

        bulk(bulkRequestBody, "update");
    }

    private void bulk(StringBuilder bulkRequestBody, String optType) {

        Map result = Maps.newHashMap();
        String router = String.format(BASIC_FORMAT, index, ES_TYPE, "_bulk");

        log.info("请求路径 -> {}, 请求数据 -> {}", router, bulkRequestBody);

//        try {
//            transportClient.bulk(bulkRequestBody);
//            response = transportClient.prepareIndex(index, ES_TYPE, "_bulk").setSource(bulkRequestBody).get();
//            result = objectMapper.readValue(response.getResult(), Map.class);
//        } catch (IOException e) {
//            log.info("ES批量插入异常", e);
//        }

        List<Map> itemResults = ((List<Map>) result.get("items")).stream().map(p -> (Map) p.get(optType)).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(itemResults)) {
            return;
        }

        List errorMsgs = itemResults.stream().filter(p -> null != p.get(ERROR_ATTR)).map(this::getErrorMsg).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorMsgs)) {
            log.warn("数据插入失败 -> {}", errorMsgs);
        }

        log.info("批处理完成，总条数： {}", itemResults.size());

    }

    private String getUpsertDoc(String innerTableName, EsOrderMain order) {

        boolean isNested = EsOrderTableConstant.OMS_DISTRIBUTION_DETAIL.equals(innerTableName);

        return EsUpdateBuildUtils.buildUpsert(order, isNested);
    }

    private void delete(String orderId) {

        String router = String.format(BASIC_FORMAT, index, ES_TYPE, orderId);

        log.info("请求路径 -> {}, 删除文档 -> {}", router, orderId);

        try {
            transportClient.prepareDelete(index, ES_TYPE, orderId).get();
        } catch (Exception e) {
            log.error("删除文档异常,orderId->{}", orderId, e);
        }
    }

    public Map<String, Object> getOrderById(String orderId) {

        QueryBuilder queryBuilder = QueryBuilders.matchQuery(EsOrderFieldConstant.ORDER_ID, orderId);

        Map<String, Object> result = Maps.newHashMap();

        try {
            result = query(queryBuilder, 1, 1, null);
        } catch (EsException e) {
            log.error(e);
        }

        List esOrders = (List) result.get("hits");

        if (CollectionUtils.isEmpty(esOrders)) {
            return Maps.newHashMap();
        }

        Map doc = (Map) esOrders.get(0);

        return Optional.ofNullable((Map) doc.get(EsOrderFieldConstant.ES_SOURCE)).orElse(Maps.newHashMap());
    }

    @SuppressWarnings("unchecked")
    public Map listOrdersByIds(List<String> orderIds) {

        if (CollectionUtils.isEmpty(orderIds)) {
            return Maps.newHashMap();
        }

        String router = String.format(BASIC_FORMAT, index, ES_TYPE, "_mget");

        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> param = Maps.newHashMap();
        param.put("ids", orderIds);

//        try {
//            Response response = restClient.performRequest(GET, router, Collections.emptyMap(),
//                    new StringEntity(JSONObject.toJSONString(param), ContentType.APPLICATION_JSON));
//            result = objectMapper.readValue(response.getEntity().getContent(), Map.class);
//        } catch (IOException e) {
//            log.error(e);
//        }

        List<Map> docs = (List<Map>) result.get("docs");

        return docs.stream()
                .filter(doc -> (Boolean) doc.get("found"))
                .map(doc -> doc.get("_source"))
                .collect(Collectors.toMap(doc -> ((Map) doc).get("order_id"), Function.identity()));

    }

    @SuppressWarnings("unchecked")
    private List<Map> listByParam(Map param, int page) {

        QueryBuilder queryBuilder = QueryBuilders.matchQuery((String) param.get(EsOrderFieldConstant.PARAM_NAME),
                param.get(EsOrderFieldConstant.PARAM_VALUE));

        Map result = Maps.newHashMap();
        EsSort sort = new EsSort().setName(EsOrderFieldConstant.ORDER_ID).setOrder(SortOrder.DESC);

        try {
            result = query(queryBuilder, page, BATCH_SIZE, sort);
        } catch (EsException e) {
            log.error("查询es出错 ->", e);
        }

        return (List) result.get("hits");
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
     * 从更新的数据中构建出ExtendOrderHeader
     */
    public EsOrderMain getOrderFromMap(String tableName, Map modifyData) {

        JSONObject orderJson = new JSONObject(modifyData);

        EsOrderMain order = new EsOrderMain();

        if (EsOrderTableConstant.OMS_ORDER_MAIN.equals(tableName)) {

            order = JSONObject.toJavaObject(orderJson, EsOrderMain.class);

        } else if (EsOrderTableConstant.OMS_ORDER_PAYINFO.equals(tableName)) {

            EsOrderPayment esOrderPayment = JSONObject.toJavaObject(orderJson, EsOrderPayment.class);
            order.setOrderPayment(esOrderPayment);

        } else if (EsOrderTableConstant.OMS_ORDER_DETAIL.equals(tableName)) {

            EsOrderDetail esOrderItem = JSONObject.toJavaObject(orderJson, EsOrderDetail.class);
            order.setOrderDetails(Lists.newArrayList(esOrderItem));

        } else if (EsOrderTableConstant.OMS_ORDER_BUYERINFO.equals(tableName)) {

            EsOrderBuyer esOrderBuyer = JSONObject.toJavaObject(orderJson, EsOrderBuyer.class);
            order.setOrderBuyer(esOrderBuyer);

        } else if (EsOrderTableConstant.OMS_ORDER_STATUS.equals(tableName)) {

            EsOrderStatus esOrderStatus = JSONObject.toJavaObject(orderJson, EsOrderStatus.class);
            order.setOrderStatus(esOrderStatus);

        } else if (EsOrderTableConstant.OMS_ORDER_TYPEINFO.equals(tableName)) {

            EsOrderType esOrderType = JSONObject.toJavaObject(orderJson, EsOrderType.class);
            order.setOrderType(esOrderType);
        }

        return order;
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
                if (StringUtils.equals(String.valueOf(esOrderDetail.getOrderItemSeqId()), String.valueOf(esOrderItem.getOrderItemSeqId()))) {
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

    /**
     * 根据表名获取需要的字段
     */
    public List getEsFields(String tableName) {
        switch (tableName) {
            //订单
            case EsOrderTableConstant.OMS_ORDER_MAIN:
                return EsFieldUtils.getEsFields(EsOrderMain.class);
            case EsOrderTableConstant.OMS_ORDER_BUYERINFO:
                return EsFieldUtils.getEsFields(EsOrderBuyer.class);
            case EsOrderTableConstant.OMS_ORDER_DETAIL:
                return EsFieldUtils.getEsFields(EsOrderDetail.class);
            case EsOrderTableConstant.OMS_ORDER_PAYINFO:
                return EsFieldUtils.getEsFields(EsOrderPayment.class);
            case EsOrderTableConstant.OMS_ORDER_STATUS:
                return EsFieldUtils.getEsFields(EsOrderStatus.class);
            case EsOrderTableConstant.OMS_ORDER_TYPEINFO:
                return EsFieldUtils.getEsFields(EsOrderType.class);
            //配货单
            case EsOrderTableConstant.OMS_DISTRIBUTION_DETAIL:
                return EsFieldUtils.getEsFields(EsDistributionDetail.class);
            case EsOrderTableConstant.OMS_DISTRIBUTION_ORDER:
                return EsFieldUtils.getEsFields(EsDistributionOrder.class);
            default:
                return Collections.emptyList();
        }
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
