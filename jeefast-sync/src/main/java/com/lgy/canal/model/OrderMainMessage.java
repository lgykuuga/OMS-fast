package com.lgy.canal.model;


import com.lgy.model.order.EsOrderMain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/24
 */
@Data
@NoArgsConstructor
public class OrderMainMessage {

    private EsOrderMain orderMain;

    private String tableName;

    private String recordType;

    public OrderMainMessage(EsOrderMain orderMain, String tableName, String recordType) {
        this.orderMain = orderMain;
        this.tableName = tableName;
        this.recordType = recordType;
    }
}
