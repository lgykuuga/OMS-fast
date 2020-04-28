package com.lgy.es.enums;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
public enum EsFieldTypeEnum {
  //字符串，分词
  TEXT,
  //字符串，不分词
  KEYWORD,
  //日期
  DATE,
  //数字类型,会被统计
  NUM,
  //布尔
  BOOL,
  //对象
  OBJECT,
  //嵌套
  NESTED,
}
