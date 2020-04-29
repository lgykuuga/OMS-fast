package com.lgy.canal.scheduling;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.Message;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lgy.canal.biz.EsSearchEngineBiz;
import com.lgy.canal.common.RecordBuffer;
import com.lgy.canal.config.EsThreadPoolConfig;
import com.lgy.canal.constant.EsOrderFieldConstant;
import com.lgy.canal.constant.EsOrderTableConstant;
import com.lgy.canal.handler.OrderRecordHandler;
import com.lgy.canal.model.OrderMainMessage;
import com.lgy.model.order.EsOrderMain;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @Description Scheduled
 * @Author LGy
 * @Date 2020/4/23
 */
@Service
@ConditionalOnProperty(name = "lgy.canal", havingValue = "0", matchIfMissing = true)
public class CanalScheduling {

    private static final Logger logger = LoggerFactory.getLogger(CanalScheduling.class);

    /**
     * 过滤订单条件
     */
    private final Predicate<CanalEntry.Column> predicate = column -> (column.getValue() != null && EsOrderFieldConstant.ORDER_ID.equalsIgnoreCase(column.getName()));

    @Autowired
    EsSearchEngineBiz esSearchEngineBiz;

    @Autowired
    OrderRecordHandler orderRecordHandler;

    /**
     * 订单
     */
    private RecordBuffer orderRecordBuffer;
    /**
     * 配货单
     */
    private RecordBuffer distributionRecordBuffer;

    /**
     * 需要同步的表
     */
    private static final Set<String> IGNORE_INSERT_TABLES;

    private static final Pattern PATTERN = Pattern.compile("[0-9]*");
    private static final int CANAL_TYPE_DATETIME = 93;
    private static final int CANAL_TYPE_DECIMAL = 3;

    /**
     * 需要同步的操作
     */
    private static final Set<CanalEntry.EventType> ACCEPT_OPERATE = Sets.newHashSet(
            CanalEntry.EventType.INSERT, CanalEntry.EventType.UPDATE, CanalEntry.EventType.DELETE);

    static {

        /**
         * 把需要同步的表写入
         * */
        IGNORE_INSERT_TABLES = Sets.newHashSet();

        Field[] fields = EsOrderTableConstant.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                IGNORE_INSERT_TABLES.add(field.get(field.getName()).toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public CanalScheduling() {
        ThreadPoolTaskExecutor executor = EsThreadPoolConfig.getThreadPoolTaskExecutor();
        orderRecordBuffer = new RecordBuffer(os -> orderRecordHandler.handle(os), executor);
    }

    @Resource
    private CanalConnector canalConnector;

    public void run() {
        int batchSize = 1000;
        Message message = canalConnector.getWithoutAck(batchSize);
        long batchId = message.getId();
        logger.trace("scheduled_batchId=" + batchId);
        try {
            List<Entry> entries = message.getEntries();
            if (batchId != -1 && entries.size() > 0) {
                entries.forEach(entry -> {
                    if (entry.getEntryType() == EntryType.ROWDATA) {
                        publishCanalEvent(entry);
                    }
                });
            }
            canalConnector.ack(batchId);
        } catch (Exception e) {
            logger.error("发送监听事件失败：" + batchId, e);
//            canalConnector.rollback(batchId);
        }
    }

    private void publishCanalEvent(Entry entry) {

        if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN
                || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
            return;
        }

        CanalEntry.RowChange rowChange = null;

        try {
            rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            logger.error("canal sync error", e);
        }

        if (null == rowChange) {
            return;
        }

        final CanalEntry.EventType eventType = rowChange.getEventType();
        final String tableName = entry.getHeader().getTableName();

        //过滤其它操作和其它表
        if (!isHandle(eventType, tableName)) {
            return;
        }

        for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
            consume(eventType, tableName, rowData);
        }
    }


    /**
     * 排除非INSERT、UPDATE、DELETE操作
     * 非 特定表操作
     */
    private boolean isHandle(CanalEntry.EventType eventType, String tableName) {
        return ACCEPT_OPERATE.contains(eventType) && IGNORE_INSERT_TABLES.contains(tableName);
    }


    private void consume(CanalEntry.EventType eventType, String tableName, CanalEntry.RowData rowData) {

        if (CanalEntry.EventType.DELETE.equals(eventType)) {
            deleteHandler(rowData.getBeforeColumnsList(), tableName);
            return;
        }

        EsOrderMain order = getEsOrder(rowData.getAfterColumnsList(), tableName);

        if (order == null) {
            return;
        }

        if (CanalEntry.EventType.UPDATE.equals(eventType)) {
            isMaxQueue(orderRecordBuffer);
            orderRecordBuffer.offer(new OrderMainMessage(order, tableName, eventType.toString()));
            return;
        }

        if (CanalEntry.EventType.INSERT.equals(eventType)) {
            isMaxQueue(orderRecordBuffer);
            orderRecordBuffer.offer(new OrderMainMessage(order, tableName, eventType.toString()));
        }
    }

    private EsOrderMain getEsOrder(List<CanalEntry.Column> columns, String tableName) {

        String orderId = columns.stream().filter(predicate).map(CanalEntry.Column::getValue).findFirst().orElse(null);

        Map<String, Object> modifyData = extractModifyData(columns, tableName);

        EsOrderMain order = esSearchEngineBiz.getOrderFromMap(tableName, modifyData);

        if (EsOrderFieldConstant.EMPTY_JSON.equals(JSONObject.toJSONString(order))) {
            return null;
        }

        order.setOrderId(orderId);

        return order;
    }


    private void deleteHandler(List<CanalEntry.Column> columns, String tableName) {

        String orderId = columns.stream().filter(predicate).map(CanalEntry.Column::getValue).findFirst().orElse(null);

        Map<String, Object> order = esSearchEngineBiz.getOrderById(orderId);

        //针对非order_header数据变化，需要设置所属orderId
        if (MapUtils.isEmpty(order)) {
            order.put(EsOrderFieldConstant.ORDER_ID, orderId);
        }

        Map<String, Object> modifyData = extractModifyData(columns, tableName);

        esSearchEngineBiz.delete(modifyData, JSONObject.toJavaObject(new JSONObject(order), EsOrderMain.class), tableName);
    }

    private Map<String, Object> extractModifyData(List<CanalEntry.Column> columns, String tableName) {

        if (CollectionUtils.isEmpty(columns)) {
            return Maps.newHashMap();
        }

        Map<String, Object> modifyDatas = Maps.newHashMap();

        List esFieldNames = esSearchEngineBiz.getEsFields(tableName);

        extractModifyData(columns, modifyDatas, esFieldNames);

        return modifyDatas;
    }

    private void extractModifyData(List<CanalEntry.Column> columns, Map<String, Object> modifyDatas, List esFieldNames) {

        columns.forEach(column -> {

            String columnName = column.getName();
            String columnValue = column.getValue();
            String esFieldName = StringUtils.lowerCase(columnName);

            if (!esFieldNames.contains(esFieldName)) {
                return;
            }

            if (StringUtils.isBlank(columnValue)) {
                modifyDatas.put(esFieldName, "");
                return;
            }

            dealTypes(modifyDatas, column, columnValue, esFieldName);

        });
    }

    private void dealTypes(Map<String, Object> modifyDatas, CanalEntry.Column column, String columnValue, String esFieldName) {
        // 日期类型
        if (CANAL_TYPE_DATETIME == column.getSqlType()) {

            Long timeStamp = null;

            try {
                timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(columnValue).getTime();
            } catch (ParseException e) {
                logger.error("日期转换异常->", e);
            }

            modifyDatas.put(esFieldName, timeStamp);
            return;
        }
        // BigDecimal类型，ES中对应double
        if (CANAL_TYPE_DECIMAL == column.getSqlType()) {
            modifyDatas.put(esFieldName, Double.parseDouble(columnValue));
            return;
        }
        modifyDatas.put(esFieldName, columnValue);
    }

    private void isMaxQueue(RecordBuffer orderRecordBuffer) {
        if (EsOrderFieldConstant.MAX_QUEUE_SIZE < orderRecordBuffer.size()) {
            logger.error("订阅同步缓冲队列数据太大，超过40960，当前线程休眠20S!!!");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                logger.error("订阅同步缓冲队列数据太大，超过40960，当前线程休眠20S!!!", e);
                Thread.currentThread().interrupt();
            }
        }
    }


}
