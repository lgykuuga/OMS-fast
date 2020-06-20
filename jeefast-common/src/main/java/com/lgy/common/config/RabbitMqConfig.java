package com.lgy.common.config;

import com.lgy.common.constant.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description RabbitMqConfig
 * @Author LGy
 * @Date 2020/1/14 15:35
 **/
@Configuration
@ConditionalOnProperty(name = "lgy.rabbitmq", havingValue = "0", matchIfMissing = true)
public class RabbitMqConfig {

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 并发消费者的初始化值
     */
    @Value("${spring.rabbitmq.listener.concurrency}")
    private int concurrency;

    /**
     * 并发消费者的最大值
     */
    @Value("${spring.rabbitmq.listener.max-concurrency}")
    private int max_concurrency;

    /**
     * 每个消费者每次监听时可拉取处理的消息数量
     */
    @Value("${spring.rabbitmq.listener.prefetch}")
    private int prefetch;


    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     *
     * @return
     */
    @Bean(name = Method.SINGLE)
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多个消费者
     *
     * @return
     */
    @Bean(name = Method.MULTI)
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setMessageConverter(converter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(concurrency);
        factory.setMaxConcurrentConsumers(max_concurrency);
        factory.setPrefetchCount(prefetch);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                logger.debug("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause)
        );
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                logger.error("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message)
        );
        return rabbitTemplate;
    }

    /**
     * 将Message由序列化结果转化为Json数据。
     *
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 转单队列
     */
    public static final String CONVERT_QUEUE_NAME = "convert.queue";
    public static final String CONVERT_EXCHANGE = "convert.exchange";
    public static final String CONVERT_ROUTING_KEY = "convert.queue";

    /**
     * 声明创建一个转单队列
     *
     * @return
     */
    @Bean
    public Queue convertQueue() {
        return new Queue(CONVERT_QUEUE_NAME, true);
    }

    /**
     * 声明创建一个转单交换器
     */
    @Bean
    public DirectExchange convertExchange() {
        return new DirectExchange(CONVERT_EXCHANGE, true, false);
    }

    /**
     * 声明转单绑定
     */
    @Bean
    public Binding convertBinding() {
        return BindingBuilder.bind(convertQueue()).to(convertExchange()).with(CONVERT_ROUTING_KEY);
    }

    /**
     * 审单队列
     */
    public static final String AUDIT_QUEUE_NAME = "audit.queue";
    public static final String AUDIT_EXCHANGE = "audit.exchange";
    public static final String AUDIT_ROUTING_KEY = "audit.queue";

    /**
     * 声明创建一个审单队列
     *
     * @return
     */
    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE_NAME, true);
    }

    /**
     * 声明创建一个审单交换器
     */
    @Bean
    public DirectExchange auditExchange() {
        return new DirectExchange(AUDIT_EXCHANGE, true, false);
    }

    /**
     * 声明审单绑定
     */
    @Bean
    public Binding auditBinding() {
        return BindingBuilder.bind(convertQueue()).to(convertExchange()).with(AUDIT_ROUTING_KEY);
    }


}
