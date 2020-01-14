package com.lgy.common.annotation;

import com.lgy.common.constant.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Description MongoDB注解,若开启mongoDB配置,
 *  * 则把数据存入mongoDB
 * @Author LGy
 * @Date 2020/1/13
 */
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoDB {

    /**
     * 设置mongoDB集合
     * */
    String document();

    /**
     * 方法
     */
    String method() default Method.ADD;
}
