package com.lgy.es.annotation;


import com.lgy.es.enums.EsFieldTypeEnum;

import java.lang.annotation.*;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsField {

  //ES数据类型
  EsFieldTypeEnum fieldType();

  //nested数据类型专用,能确定数组中的唯一一个元素
  boolean isKey() default false;

}
