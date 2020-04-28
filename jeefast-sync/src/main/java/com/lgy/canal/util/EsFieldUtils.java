package com.lgy.canal.util;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description 反射Utils
 * @Author LGy
 * @Date 2020/1/22 10:43
 **/
public class EsFieldUtils {


    /**
     * 根据类反射获取需要的字段
     */
    public static List getEsFields(Class clazz) {

        List<Field> fields = new ArrayList<>();

        Class superClass = clazz;
        while (superClass != null) {
            fields.addAll(new ArrayList<>(Arrays.asList(superClass.getDeclaredFields())));
            superClass = superClass.getSuperclass();
        }

        List<String> fieldNames = new ArrayList<>();

        fields.forEach(field -> {
            String fieldName = Optional.ofNullable(field.getAnnotation(JSONField.class)).map(JSONField::name).orElse(null);
            if (StringUtils.isNotBlank(fieldName)) {
                fieldNames.add(fieldName);
            }
        });

        return fieldNames;
    }


}
