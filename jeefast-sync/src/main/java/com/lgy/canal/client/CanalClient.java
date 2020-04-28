package com.lgy.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.google.common.collect.Lists;
import com.lgy.canal.scheduling.CanalScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * @Description Canal客户端
 * @Author LGy
 * @Date 2020/4/23
 */
@Component
@ConditionalOnProperty(name = "lgy.canal", havingValue = "0", matchIfMissing = true)
public class CanalClient implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(CanalClient.class);

    private CanalConnector canalConnector;

    @Value("${canal.host}")
    private String canalHost;
    @Value("${canal.port}")
    private String canalPort;
    @Value("${canal.destination}")
    private String canalDestination;
    @Value("${canal.username}")
    private String canalUsername;
    @Value("${canal.password}")
    private String canalPassword;

    @Autowired
    CanalScheduling canalScheduling;

    @Bean
    public CanalConnector getCanalConnector() {
        // 监听的表
        canalConnector = CanalConnectors.newClusterConnector(Lists.newArrayList(
                new InetSocketAddress(canalHost, Integer.valueOf(canalPort))),
                canalDestination, canalUsername, canalPassword);
        canalConnector.connect();
        // 指定filter，格式 {database}.{table}，这里不做过滤，过滤操作留给用户
        canalConnector.subscribe();
        logger.info("canal客户端启动成功");
        /**
         * 回滚寻找上次中断的位置, 出错不进行回滚，可能导致循环报错，如有出错会通过定时任务去补偿
         */
        return canalConnector;
    }

    @Override
    public void destroy() {
        if (canalConnector != null) {
            canalConnector.disconnect();
        }
    }


    public void boot() {
        try {
            logger.info("canal开始监听");
            while (true) {
                canalScheduling.run();
            }
        } catch (Exception e) {
            logger.error("canal异常", e);
        } finally {
            destroy();
        }
    }

}
