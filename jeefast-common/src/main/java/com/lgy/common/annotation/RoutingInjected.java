package com.lgy.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * RoutingInjected
 * 
 * @author LGy
 * @date 2020-10-13
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingInjected {


}
