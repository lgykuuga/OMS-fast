package com.lgy.canal.common;

import com.lgy.canal.client.CanalClient;
import com.lgy.common.config.SystemConfig;
import lombok.extern.log4j.Log4j2;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 启动canal or DTS
 * @Author LGy
 * @Date 2020/4/28
 */
@Log4j2
@Component
public class CommonStarter implements CommandLineRunner {

    @Autowired(required = false)
    CanalClient canalBoot;

    @Autowired
    SystemConfig systemConfig;

    /**
     * canal线程池
     */
    @Resource(name = "canal")
    ThreadPoolTaskExecutor canalThreadPool;

    @Value("${spring.profiles.active:dev}")
    private String profile;

    private static final String ACTIVE_PROD = "prod";


    @Override
    public void run(String... strings) {

        // 非正式环境,用canal同步数据,正式环境用dts同步
        if (systemConfig.isOpenCanal() && !ACTIVE_PROD.equals(profile)) {
            canalThreadPool.execute(() -> canalBoot.boot());
        }
        return;

        // 以后其它环境通过dts数据订阅
    }

}
