package com.lgy.canal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description Es线程池
 * @Author LGy
 * @Date 2020/4/25
 */
@Configuration
public class EsThreadPoolConfig {


    @Bean(name="es-sync")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();

        final int nTreads = Runtime.getRuntime().availableProcessors() * 2 +1;

        pool.setKeepAliveSeconds(500);
        //核心线程池数
        pool.setCorePoolSize(nTreads);
        // 最大线程
        pool.setMaxPoolSize(nTreads * 2);
        //队列容量
        pool.setQueueCapacity(10);
        //队列满，线程被拒绝执行策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        pool.setThreadNamePrefix("Es-syncThreadPool-");
        return pool;
    }

    @Bean(name="canal")
    public ThreadPoolTaskExecutor canalThreadPool(){
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();

        pool.setKeepAliveSeconds(1);
        //核心线程池数
        pool.setCorePoolSize(1);
        // 最大线程
        pool.setMaxPoolSize(1);
        //队列容量
        pool.setQueueCapacity(1);
        //队列满，线程被拒绝执行策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        pool.setThreadNamePrefix("canal-");
        return pool;
    }

    public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.initialize();
        pool.setKeepAliveSeconds(500);
        pool.setCorePoolSize(1);
        pool.setMaxPoolSize(1);
        pool.setQueueCapacity(1);
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        pool.setThreadNamePrefix("Es-syncThreadPool-");
        return pool;
    }
}
