package com.lgy.oms.disruptor.audit;

import com.alibaba.fastjson.JSON;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.config.CustomThreadFactoryBuilder;
import com.lgy.oms.disruptor.tracelog.TraceLogEvent;
import com.lgy.oms.disruptor.tracelog.TraceLogHandler;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.system.domain.SysUser;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ThreadFactory;

/**
 * @Author LGy
 * @Date 2019/12/30
 * @Description 审单Util
 */
@Service
public class AuditDisruptorUtil {

    private static Logger logger = LoggerFactory.getLogger(AuditDisruptorUtil.class);

    private Disruptor<OrderNumberEvent> disruptor;
    private static final int RING_BUFFER_SIZE = 1024 * 1024;

    @Autowired
    private AuditHandler auditHandler;

    private Producer producer;

    /**
     * 销毁前执行
     */
    @PreDestroy
    public void destroy() {
        disruptor.shutdown();
        logger.info("disruptor of auditEvent shutdown");
    }

    /**
     * 启动执行
     */
    @PostConstruct
    public void afterPropertiesSet() {
        ThreadFactory threadFactory = new CustomThreadFactoryBuilder()
                .setNamePrefix("auditEvent")
                .setDaemon(false)
                .build();

        disruptor = new Disruptor<>(
                OrderNumberEvent::new,
                RING_BUFFER_SIZE,
//                DaemonThreadFactory.INSTANCE,
                threadFactory,
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );

        //创建消费者组
        disruptor.handleEventsWith(auditHandler);
        //启动disruptor
        disruptor.start();
        this.producer = new Producer(disruptor.getRingBuffer());
        logger.info("disruptor of auditEvent start");
    }

    public Producer getProducer() {
        return producer;
    }

    public class Producer {
        private RingBuffer<OrderNumberEvent> ringBuffer;

        Producer(RingBuffer<OrderNumberEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void onData(OrderNumberEvent t) {
            logger.debug("publish auditEvent [{}]", JSON.toJSONString(t.getOrderId()));
            long sequence = ringBuffer.next();
            try {
                OrderNumberEvent t1 = ringBuffer.get(sequence);
                BeanUtils.copyProperties(t, t1);
                //设置用户信息,用于线程切换
                SysUser sysUser = ShiroUtils.getSysUser();
                t1.setSysUser(sysUser);
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

}
