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

    private String msg;
    private String state;

    public final String ERROR_CODE = "0";

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getERROR_CODE() {
        return ERROR_CODE;
    }
}