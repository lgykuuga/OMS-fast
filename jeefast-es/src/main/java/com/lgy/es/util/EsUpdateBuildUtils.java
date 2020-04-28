package com.lgy.es.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lgy.es.annotation.EsField;
import com.lgy.es.enums.EsFieldTypeEnum;
import com.lgy.es.common.EsModel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Log4j2
public class EsUpdateBuildUtils {

  private static final String ITEM = "item";

  private static final String NESTED_SCRIPT = "if(ctx._source.{0} == null)"
      + "'{'ctx._source.{0}=[];'}'"
      + "for (def current_item : ctx._source.{0})'{'"
        + "def current_item_id = current_item.{1};"
        + "def update_item = params.item[current_item_id];"
        + "if(update_item != null)'{'"
          + "for(def entry : update_item.entrySet())'{'"
              + "current_item[entry.getKey()] = entry.getValue()"
            + "'}'"
            + "params.item.remove(current_item_id)"
        + "'}"
      + "}'"
      + "for(def entry : params.item.entrySet())'{'"
        + "ctx._source.{0}.add(entry.getValue())"
      + "'}'";

  private static Object converterDate(Object object) {

    if (object.getClass() == Date.class) {
     return ((Date) object).getTime();
    }
    return object;
  }


  public static String buildUpsert(EsModel esModel, boolean isNested) {

    JSONObject main = new JSONObject();

    JSONObject doc = new JSONObject();
    JSONObject script = new JSONObject();

    //脚本更新和文档更新不能同时使用
    if (isNested){
      main.put("script", script);
    }else {
      main.put("doc", doc);
      main.put("doc_as_upsert", true);
    }

    buildParam(esModel, doc, script);
    return main.toString();
  }

  private static void buildParam(Object model, JSONObject doc, JSONObject script) {
    //获取子父类field
    List<Field> fields = new ArrayList<>(26);
    Class clazz = model.getClass();
    while (clazz != Object.class) {
      fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
      clazz = clazz.getSuperclass();
    }

    //根据属性封装queryBuild
    for (Field field : fields) {
      field.setAccessible(true);
      EsField esField = field.getAnnotation(EsField.class);
      JSONField jsonField = field.getAnnotation(JSONField.class);

      if (esField == null || jsonField == null){
        continue;
      }

      //字段Es类型
      EsFieldTypeEnum fieldType = esField.fieldType();
      String fieldName = jsonField.name();

      //字段值
      Object fieldValue = null;
      try {
        fieldValue = field.get(model);
      } catch (IllegalAccessException e) {
        log.error("获取参数异常,{}", e);
      }
      if (fieldValue == null) {
        continue;
      }

      try {
        switch (fieldType) {
          case NESTED:
            buildScript(new ArrayList((List) fieldValue), field, script);
            break;
          case OBJECT:
            JSONObject o_obj = new JSONObject();
            doc.put(fieldName, o_obj);
            buildParam(fieldValue, o_obj, script);
            break;
          default:
            doc.put(fieldName, converterDate(fieldValue));
            break;
        }
      } catch (IllegalAccessException e) {
        log.error("格式转化失败,{}", fieldValue, e);
      }
    }
  }

  public static void buildScript(List<Object> models, Field modelsField, JSONObject script)
      throws IllegalAccessException {
    if (CollectionUtils.isEmpty(models)) {
      return;
    }
    JSONObject params = new JSONObject();
    script.put("lang", "painless");
    script.put("params", params);

    //获取list的泛型
    Class<?> genericClazz = null;
    Type genericType = modelsField.getGenericType();
    if (genericType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) genericType;
      genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
    }

    //获取条目的主键
    Field keyField = null;
    String keyName = null;
    for (Field field : genericClazz.getDeclaredFields()) {
      field.setAccessible(true);
      EsField esField = field.getAnnotation(EsField.class);
      if (esField.isKey()) {
        JSONField jsonField = field.getAnnotation(JSONField.class);
        keyField = field;
        keyName = jsonField.name();
        break;
      }
    }

    //条目数据
    JSONObject item = new JSONObject();

    Field keyField_ = keyField;
    models.stream().forEach(model -> {
      try {
        item.put((String) keyField_.get(model),model);
      } catch (IllegalAccessException e) {
        log.error("格式转化失败,{},{}", keyField_, model, e);
      }
    });
    params.put(ITEM, item);

    script.put("inline", MessageFormat
        .format(NESTED_SCRIPT, modelsField.getAnnotation(JSONField.class).name(), keyName));
  }

}
