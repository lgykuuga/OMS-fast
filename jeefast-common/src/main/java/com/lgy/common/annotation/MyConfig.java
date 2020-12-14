package com.lgy.common.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;


/**
 * @Description 判断设置是否加载缓存
 * @Author LGy
 * @Date 2020/1/13
 */
@Configuration
public class MyConfig implements BeanPostProcessor {

    @Autowired
    ApplicationContext applicationContext;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeforeInit加载了bean " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {


        System.out.println("AfterInit加载了bean " + beanName);
        Class clazz = bean.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();



        return bean;
    }



}
