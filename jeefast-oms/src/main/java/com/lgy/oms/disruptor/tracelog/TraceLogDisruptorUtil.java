package com.lgy.oms.disruptor.tracelog;

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
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadFactory;

/**
 * @Author LGy
 * @Date 2019/30/30
 * @Description 轨迹日志Util
 */
@Service
public class TraceLogDisruptorUtil implements DisposableBean, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(TraceLogDisruptorUtil.class);

    private Disruptor<TraceLogEvent> disruptor;
    private static final int RING_BUFFER_SIZE = 1024 * 1024;

    @Autowired
    private TraceLogHandler traceLogHandler;

    private Producer producer;


    @Override
    public void destroy() {
        disruptor.shutdown();
        logger.info("disruptor of traceLogEvent shutdown");
    }

    @Override
    public void afterPropertiesSet() {
        ThreadFactory threadFactory = new CustomThreadFactoryBuilder()
                .setNamePrefix("traceLogEvent")
                .setDaemon(false)
                .build();

        disruptor = new Disruptor<>(
                TraceLogEvent::new,
                RING_BUFFER_SIZE,
//                DaemonThreadFactory.INSTANCE,
                threadFactory,
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );

        //异常处理
        disruptor.setDefaultExceptionHandler(new TraceLogExceptionHandler());
        //创建消费者组
        disruptor.handleEventsWith(traceLogHandler);
        //启动disruptor
        disruptor.start();
        this.producer = new Producer(disruptor.getRingBuffer());
        logger.info("disruptor of traceLogEvent start");
    }

    public Producer getProducer() {
        return producer;
    }

    public class Producer {

        private RingBuffer<TraceLogEvent> ringBuffer;

        Producer(RingBuffer<TraceLogEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void onData(TraceLogEvent t) {
            logger.debug("publish TraceLogEvent [{}]", JSON.toJSONString(t.getTraceLog()));
            long sequence = ringBuffer.next();
            try {
                TraceLogEvent t1 = ringBuffer.get(sequence);
                BeanUtils.copyProperties(t, t1);
                //设置用户信息,用于线程切换
                SysUser sysUser = ShiroUtils.getSysUser();
                t1.setSysUser(sysUser);
            } catch (Exception e) {
                logger.error("Exception:" + e);
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

}
