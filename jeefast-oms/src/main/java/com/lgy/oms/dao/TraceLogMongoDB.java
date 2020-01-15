package com.lgy.oms.dao;

import com.alibaba.fastjson.JSON;
import com.lgy.common.dao.AbstractMongoDBDao;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.domain.TraceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 操作日志MongoDB业务
 * @Author LGy
 * @Date 2020/1/13 14:53
 **/
@Service(OrderModuleConstants.TRACE_LOG_MongoDB)
public class TraceLogMongoDB extends AbstractMongoDBDao<TraceLog> {

    private static Logger logger = LoggerFactory.getLogger(TraceLogMongoDB.class);

    @Override
    protected Class<TraceLog> getEntityClass() {
        return TraceLog.class;
    }

    @Override
    public void save(TraceLog entity) {
        if (StringUtils.isEmpty(entity.getOrderId())) {
            logger.error("订单号不能为空[{}]", JSON.toJSONString(entity));
            throw new NullPointerException("订单号不能为空");
        }
        if (StringUtils.isEmpty(entity.getCreateBy())) {
            entity.setCreateBy(ShiroUtils.getSysUser().getUserName());
        }
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(DateUtils.getNowDate());
        }
        super.save(entity);
    }

}
