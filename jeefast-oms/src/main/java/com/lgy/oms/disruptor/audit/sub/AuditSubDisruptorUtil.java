package com.lgy.oms.disruptor.audit.sub;

import com.alibaba.fastjson.JSON;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.config.CustomThreadFactoryBuilder;
import com.lgy.oms.disruptor.audit.AuditOrderEvent;
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
import java.util.concurrent.ThreadFactory;

/**
 * @Author LGy
 * @Date 2020/1/8
 * @Description 审单子流程Util
 * <p>
 * 指定等待策略
 * Disruptor 定义了 com.lmax.disruptor.WaitStrategy 接口用于抽象 Consumer 如何等待新事件，这是策略模式的应用。
 * Disruptor 提供了多个 WaitStrategy 的实现，每种策略都具有不同性能和优缺点，根据实际运行环境的 CPU 的硬件特点选择恰当的策略，并配合特定的 JVM 的配置参数，能够实现不同的性能提升。
 * 例如，BlockingWaitStrategy、SleepingWaitStrategy、YieldingWaitStrategy 等，其中，
 * BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现；
 * SleepingWaitStrategy 的性能表现跟 BlockingWaitStrategy 差不多，对 CPU 的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景；
 * YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于 CPU 逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性。
 */
@Service
public class AuditSubDisruptorUtil {

    private static Logger logger = LoggerFactory.getLogger(AuditSubDisruptorUtil.class);

    private Disruptor<AuditOrderEvent> disruptor;
    private static final int RING_BUFFER_SIZE = 1024 * 1024;

    /**
     * 校验订单地址有效性
     */
    @Autowired
    CheckAddressHandler addressHandler;
    /**
     * 校验拦截订单主信息
     */
    @Autowired
    CheckOrderSpecialHandler orderSpecialHandler;
    /**
     * 校验拦截订单明细信息
     */
    @Autowired
    CheckOrderCommodityHandler orderCommodityHandler;
    /**
     * 更新订单信息
     */
    @Autowired
    UpdateOrderInfoHandler updateOrderInfoHandler;

    private Producer producer;

    /**
     * 销毁前执行
     */
    @PreDestroy
    public void destroy() {
        disruptor.shutdown();
        logger.info("disruptor of auditSubEvent shutdown");
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

        /**
         * 并行校验地址、校验拦截订单主信息、校验拦截订单明细信息
         * 后串行执行更新订单状态
         */

        CheckOrderCommodityHandler[] checkOrderCommodityHandlers = new CheckOrderCommodityHandler[10];
        for (int i = 0; i < 10; i++) {
            checkOrderCommodityHandlers[i] = orderCommodityHandler;
        }

        disruptor.handleEventsWithWorkerPool(checkOrderCommodityHandlers);
        disruptor.handleEventsWith(orderSpecialHandler);
        disruptor.handleEventsWith(addressHandler)
                .then(updateOrderInfoHandler);

        //异常处理
        disruptor.setDefaultExceptionHandler(new AuditSubExceptionHandler());
        //启动disruptor
        disruptor.start();
        this.producer = new Producer(disruptor.getRingBuffer());
        logger.info("disruptor of auditSubEvent start");
    }

    public Producer getProducer() {
        return producer;
    }

    public class Producer {
        private RingBuffer<AuditOrderEvent> ringBuffer;

        Producer(RingBuffer<AuditOrderEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void onData(AuditOrderEvent t) {
            logger.debug("publish auditSubEvent [{}]", JSON.toJSONString(t.getOrderMain()));
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
