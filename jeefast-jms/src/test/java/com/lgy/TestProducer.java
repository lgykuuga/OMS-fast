package com.lgy;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description TestProducer
 * @Author LGy
 * @Date 2020/6/19 17:32
 **/
public class TestProducer {

    @Autowired
    private AbstractProducer abstractProducer;


    @Test
    public void test1(){
        abstractProducer.executeA();
    }

}
