package com.lgy.oms.interfaces.rds.bean;
import lombok.Data;

import java.util.List;

/**
 * @Description 淘宝RDS推送库订单报文对象
 * @Author LGy
 * @Date 2019/12/4
 */
@Data
public class RdsOrders {

    private List<RdsOrder> order;

}