package com.lgy.oms.disruptor.audit.sub;

import com.alibaba.fastjson.JSON;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.config.CustomThreadFactoryBuilder;
import com.lgy.system.domain.SysUser;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
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
    /**
     * 发送订单至配货队列
     */
    @Autowired
    SendOrderInfo2DistributionHandler sendOrderInfo2DistributionHandler;

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
        ThreadFactory threadFactory = new CustomThreadFactoryBuilder()
                .setNamePrefix("auditSubEvent")
                .setDaemon(false)
                .build();

        disruptor = new Disruptor<>(
                AuditOrderEvent::new,
                RING_BUFFER_SIZE,
//                DaemonThreadFactory.INSTANCE,
                threadFactory,
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );

        //异常处理
        disruptor.setDefaultExceptionHandler(new AuditSubExceptionHandler());
        /**
         * 并行校验地址、校验拦截订单主信息、校验拦截订单明细信息
         * 后串行执行更新订单状态
         */
        disruptor.handleEventsWith(addressHandler, orderSpecialHandler, orderCommodityHandler)
                .then(updateOrderInfoHandler);
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
