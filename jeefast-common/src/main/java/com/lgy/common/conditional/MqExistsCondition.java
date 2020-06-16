package com.lgy.common.conditional;

import com.lgy.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Description MqExistsCondition
 * @Author LGy
 * @Date 2020/5/25 11:04
 **/
public class MqExistsCondition implements Condition {


    public Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        //从这里获取配置文件中 active 的值，根据当前的active值决定是否加载类
        String[] activeProfiles = environment.getActiveProfiles();
        environment.getDefaultProfiles();
        for (String active : activeProfiles) {
            if (Constants.ON.equals(active)) {
                log.info("启动rabbitMQ配置");
                return true;
            }
        }
        return false;
    }
}