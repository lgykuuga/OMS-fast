package com.lgy.oms.disruptor.audit;

import com.alibaba.fastjson.JSON;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.system.domain.SysUser;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author LGy
 * @Date 2019/12/30
 * @Description 审单Util
 */
@Service
public class AuditDisruptorUtil {

    private static Logger logger = LoggerFactory.getLogger(AuditDisruptorUtil.class);

    private Disruptor<AuditOrderEvent> disruptor;
    private static final int RING_BUFFER_SIZE = 1024 * 1024;

    private static final int THREAD_COUNT = 5;

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

        disruptor = new Disruptor<>(
                AuditOrderEvent::new,
                RING_BUFFER_SIZE,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new SleepingWaitStrategy()
        );

        //校验地址创建消费者组
        AuditHandler[] auditHandlers = new AuditHandler[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            auditHandlers[i] = auditHandler;
        }

        //创建消费者组
        disruptor.handleEventsWithWorkerPool(auditHandlers);

        //异常处理
        disruptor.setDefaultExceptionHandler(new AuditExceptionHandler());

        //启动disruptor
        disruptor.start();
        this.producer = new Producer(disruptor.getRingBuffer());
        logger.info("disruptor of auditEvent start");
    }

    Producer getProducer() {
        return producer;
    }

    public class Producer {
        private RingBuffer<AuditOrderEvent> ringBuffer;

        Producer(RingBuffer<AuditOrderEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        void onData(AuditOrderEvent t) {
            logger.debug("publish auditEvent [{}]", JSON.toJSONString(t.getOrderMain().getOrderId()));
            long sequence = ringBuffer.next();
            try {
                AuditOrderEvent t1 = ringBuffer.get(sequence);
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
