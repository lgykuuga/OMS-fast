package com.lgy.rabbitmq;

import com.lgy.AbstractProducer;
import org.springframework.stereotype.Service;

/**
 * @Description RabbitMqProducer
 * @Author LGy
 * @Date 2020/6/19 17:30
 **/
@Service
public class RabbitMqProducer extends AbstractProducer {


    @Override
    public void executeA() {
        System.out.println("Rabbit method A");
    }

    @Override
    public void executeD() {
        System.out.println("Rabbit method D");
    }

    @Override
    public void executeE() {
        System.out.println("Rabbit method E");
    }

    @Override
    public void executeF() {
        System.out.println("Rabbit method F");
    }
}
