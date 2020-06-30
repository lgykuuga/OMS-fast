package com.lgy.oms.factory;

import com.lgy.common.utils.DateUtils;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.domain.TraceLog;

/**
 * @Description TraceLogFactory
 * @Author LGy
 * @Date 2020/6/18 10:28
 **/
public class TraceLogFactory {

    public static TraceLog create(String module, String orderId, String type, Integer level, String content) {
        TraceLog traceLog = new TraceLog();
        traceLog.setModule(module);
        traceLog.setOrderId(orderId);
        traceLog.setType(type);
        traceLog.setLevel(level);
        traceLog.setContent(content);
        traceLog.setCreateBy(ShiroUtils.getSysUser().getUserName());
        traceLog.setCreateTime(DateUtils.getNowDate());
        return traceLog;
    }

}
