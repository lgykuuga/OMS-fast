package com.lgy.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * RoutingSwitch
 * 
 * @author LGy
 * @date 2020-10-13
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingSwitch {

    /**
     * 在配置系统中开关的属性名称,应用系统将会实时读取配置
     * @return
     */
    String value() default "";
    
}
