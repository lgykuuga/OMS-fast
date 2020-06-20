package com.lgy.disruptor;

import com.lgy.AbstractProducer;
import org.springframework.stereotype.Service;

/**
 * @Description DisruptorProducer
 * @Author LGy
 * @Date 2020/6/19 17:30
 **/
@Service
public class DisruptorProducer extends AbstractProducer {

    @Override
    public void executeA() {
        System.out.println("Disruptor A");
    }

    @Override
    public void executeB() {
        System.out.println("Disruptor B");
    }

    @Override
    public void executeC() {
        System.out.println("Disruptor C");
    }
}
