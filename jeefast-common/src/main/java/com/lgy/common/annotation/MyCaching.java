package com.lgy.common.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;


/**
 * @Description 判断设置是否加载缓存
 * @Author LGy
 * @Date 2020/1/13
 */

@EnableCaching
@ConditionalOnProperty(name = "lgy.redis", havingValue = "0", matchIfMissing = true)
public @interface MyCaching {


}
