package com.lgy.es.annotation;

import java.lang.annotation.*;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsDocument {

    //索引
    String index();

    //索引类型
    String type();
}
