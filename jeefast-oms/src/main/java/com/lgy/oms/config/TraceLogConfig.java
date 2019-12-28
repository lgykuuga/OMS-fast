package com.lgy.oms.config;

import com.lgy.oms.disruptor.TraceLogDisruptorHelper;
import com.lgy.oms.disruptor.TraceLogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description 轨迹日志发送配置
 * @Author LGy
 * @Date 2019/12/27
 */
@Configuration
public class TraceLogConfig {

    @Bean
    public TraceLogDisruptorHelper traceLogHelper() {
        return new TraceLogDisruptorHelper(1, 2048);
    }

}
