package com.lgy.canal.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.lgy.canal.model.OrderMainMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;


/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
public class RecordBuffer implements Runnable {

    private ConcurrentLinkedQueue<OrderMainMessage> orderQueue = Queues.newConcurrentLinkedQueue();

    private int batchSyncSize = 4096;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private Consumer<List<OrderMainMessage>> esSyncFunction;

    private ThreadPoolTaskExecutor executor = null;

    public RecordBuffer(Consumer<List<OrderMainMessage>> esSyncFunction,
                        ThreadPoolTaskExecutor executor) {
        this.esSyncFunction = esSyncFunction;
        this.executor = executor;
    }

    public RecordBuffer(int batchSyncSize) {
        this.batchSyncSize = batchSyncSize;
    }

    public boolean offer(OrderMainMessage orderHeader) {

        if (orderHeader == null) {
            return false;
        }

        orderQueue.offer(orderHeader);

        if (!isRunning.get()) {
            executor.execute(this);
        }

        return true;
    }

    @Override
    public void run() {

        start();

        esSync();

        stop();
    }

    private void esSync() {

        if (orderQueue.isEmpty()) {
            return;
        }

        List<OrderMainMessage> orderHeaders = Lists.newArrayList();

        while (!orderQueue.isEmpty() && orderHeaders.size() < batchSyncSize) {

            OrderMainMessage orderHeader = orderQueue.poll();

            if (orderHeader != null) {
                orderHeaders.add(orderHeader);
            }
        }

        if (CollectionUtils.isNotEmpty(orderHeaders)) {
            esSyncFunction.accept(orderHeaders);
        }

        esSync();
    }

    private void start() {

        isRunning.compareAndSet(false, true);
    }

    private void stop() {

        isRunning.compareAndSet(true, false);
    }

    public int size() {
        return this.orderQueue.size();
    }
}
