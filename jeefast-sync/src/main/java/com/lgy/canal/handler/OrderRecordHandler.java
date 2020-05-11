package com.lgy.canal.handler;


import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.collect.Maps;
import com.lgy.base.domain.Owner;
import com.lgy.base.domain.Platform;
import com.lgy.base.domain.Shop;
import com.lgy.canal.biz.EsSearchEngineBiz;
import com.lgy.canal.constant.EsOrderTableConstant;
import com.lgy.canal.model.OrderMainMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/25
 */
@Component
@Log4j2
public class OrderRecordHandler implements RecordHandler {

    @Autowired
    private EsSearchEngineBiz esSearchEngineBiz;
    @Resource(name = "es-sync")
    private ThreadPoolTaskExecutor esSyncExecutor;

    /** 插入订单表头 */
    private final Predicate<OrderMainMessage> insertOrderMainPredicate = record ->
            CanalEntry.EventType.INSERT.name().equalsIgnoreCase(record.getRecordType())
                    && EsOrderTableConstant.OMS_ORDER_MAIN.equals(record.getTableName());

    @Override
    public void handle(List<OrderMainMessage> records) {

        if (CollectionUtils.isEmpty(records)) {
            return;
        }

        try {

            Map<String, String> orderHeaderEsDocs = Maps.newLinkedHashMap();

            List<String> owners = records.stream().filter(insertOrderMainPredicate).map(p -> (String) p.getOrderMain().getOwner()).collect(Collectors.toList());
            List<String> platforms = records.stream().filter(insertOrderMainPredicate).map(p -> (String) p.getOrderMain().getPlatform()).collect(Collectors.toList());
            List<String> shops = records.stream().filter(insertOrderMainPredicate).map(p -> (String) p.getOrderMain().getShop()).collect(Collectors.toList());

            FutureTask<Map<String, Owner>> listOwnerTask = new FutureTask<>(() -> esSearchEngineBiz.listByOwnerGcoToMap(owners));
            FutureTask<Map<String, Platform>> listPlatformTask = new FutureTask<>(() -> esSearchEngineBiz.listByPlatformGcoToMap(platforms));
            FutureTask<Map<String, Shop>> listShopTask = new FutureTask<>(() -> esSearchEngineBiz.listByShopGcoToMap(shops));

            esSyncExecutor.submit(listOwnerTask);
            esSyncExecutor.submit(listPlatformTask);
            esSyncExecutor.submit(listShopTask);

            Map<String, Owner> ownerMap = listOwnerTask.get();
            Map<String, Platform> platformMap = listPlatformTask.get();
            Map<String, Shop> shopMap = listShopTask.get();

            for (OrderMainMessage record : records) {

                if (record.getOrderMain() == null || record.getOrderMain().getOrderId() == null) {
                    continue;
                }

                //新增订单时需要特殊处理（获取对应的货主档案、平台、店铺信息）
                if (insertOrderMainPredicate.test(record)) {
                    record.getOrderMain().setOwner(Optional.ofNullable(ownerMap.get(record.getOrderMain().getOwner())).orElse(null));
                    record.getOrderMain().setPlatform(Optional.ofNullable(platformMap.get(record.getOrderMain().getPlatform())).orElse(null));
                    record.getOrderMain().setShop(Optional.ofNullable(shopMap.get(record.getOrderMain().getShop())).orElse(null));
                }

                String docKey = record.getOrderMain().getOrderId() + "";

                orderHeaderEsDocs.put(docKey, esSearchEngineBiz.getOrderMainDoc(record));
            }

            esSearchEngineBiz.batchUpsert(orderHeaderEsDocs);

        } catch (Exception e) {

            log.error("执行异常：", e);
        }
    }

}
