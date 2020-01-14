package com.lgy.framework.aspectj;

import com.lgy.common.annotation.MongoDB;
import com.lgy.common.constant.Constants;
import com.lgy.common.constant.Method;
import com.lgy.common.dao.AbstractMongoDBDao;
import com.lgy.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description MongoDB注解, 若开启mongoDB配置,
 *  *  * 则把数据存入mongoDB
 * @Author LGy
 * @Date 2020/1/13
 */
@Aspect
@Component
public class MongoDBAspect {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * mongoDB配置
     */
    @Value("${lgy.mongoDB}")
    private String configuration;

    /**
     * 在方法执行之前对注解进行处理
     * @param joinPoint
     * @param mongoDB
     * @return 返回中的值
     * */
    @Around("@annotation(com.lgy.common.annotation.MongoDB) && @annotation(mongoDB)")
    public Object dealProcess(ProceedingJoinPoint joinPoint, MongoDB mongoDB) {
        Object result = null;

        //开启mongoDB设置,用mongoDB保存,否则用DB保存
        if (Constants.ON.equals(configuration)) {

            AbstractMongoDBDao mongoDBDao = (AbstractMongoDBDao) applicationContext.getBean(mongoDB.document());

            Object[] args = joinPoint.getArgs();

            if (Method.ADD.equals(mongoDB.method())) {
                mongoDBDao.save(args[0]);
            } else if (Method.GET.equals(mongoDB.method())) {
                result = mongoDBDao.queryList(args[0]);
            }
            return result;
        }

        //执行目标方法
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return result;
    }
}
