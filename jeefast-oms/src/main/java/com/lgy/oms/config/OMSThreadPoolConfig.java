package com.lgy.oms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Description 异步线程池配置
 * @Author LGy
 * @Date 2020/1/19 17:10
 **/
@Configuration
@EnableAsync
public class OMSThreadPoolConfig {

//    @Bean("convertThreadPool")
//    public ThreadPoolTaskExecutor convertThreadPool() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //核心线程数5：线程池创建时候初始化的线程数
//        executor.setCorePoolSize(5);
//        //最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
//        executor.setMaxPoolSize(5);
//        //缓冲队列500：用来缓冲执行任务的队列
//        executor.setQueueCapacity(500);
//        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
//        executor.setKeepAliveSeconds(60);
//        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
//        executor.setThreadNamePrefix("convertThreadPool-");
//        executor.initialize();
//        return executor;
//    }

    @Bean("auditThreadPool")
    public ThreadPoolTaskExecutor auditThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数5：线程池创建时候初始化的线程数
        executor.setCorePoolSize(5);
        //最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(5);
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("auditThreadPool-");
        executor.initialize();
        return executor;
    }

}

