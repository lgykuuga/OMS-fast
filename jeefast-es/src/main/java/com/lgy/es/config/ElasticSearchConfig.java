package com.lgy.es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Component
public class ElasticSearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);

    private TransportClient transportClient;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public TransportClient getTransportClient() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .build();
        transportClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
        logger.info("elasticsearch transportClient 初始化");
        return transportClient;
    }


    @PostConstruct
    void init() {
        /**
         * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
         * 解决netty冲突后初始化client时还会抛出异常
         * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
         */
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }


}