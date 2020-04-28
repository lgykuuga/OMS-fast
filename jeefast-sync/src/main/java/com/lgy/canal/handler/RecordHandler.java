package com.lgy.canal.handler;


import com.lgy.canal.model.OrderMainMessage;

import java.util.List;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/25
 */
public interface RecordHandler {

    /**
     * 处理线程池任务
     *
     * @param records 订单信息
     */
    void handle(List<OrderMainMessage> records);
}
