package com.lgy.oms.domain.dto.kjy;
import java.util.List;

/**
 * @Description 跨境翼查询订单返回消息
 * @Author LGy
 * @Date 2019/10/17
 */
public class KJYOrderResponse {

    private String has_next;
    private List<Trades> trades;
    public void setHas_next(String has_next) {
         this.has_next = has_next;
     }
     public String getHas_next() {
         return has_next;
     }

    public void setTrades(List<Trades> trades) {
         this.trades = trades;
     }
     public List<Trades> getTrades() {
         return trades;
     }

}