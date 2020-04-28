package com.lgy.es.util;

import com.alibaba.fastjson.JSON;
import com.lgy.es.annotation.EsField;
import com.lgy.es.enums.EsAggregationsOperateEnum;
import com.lgy.es.enums.EsFieldTypeEnum;
import com.lgy.es.param.EsAggregationsParam;
import com.lgy.es.result.AggregationGroupByResult;
import com.lgy.es.common.EsModel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class EsAggregationsBuildUtils {

    public static List<AggregationBuilder> buildAggregations(EsModel param,
                                                             String... groupBys) {

        //groupBy是按数组顺序一层层聚合，类似mysql
        List<AggregationBuilder> builders = new ArrayList<>();

        if (param == null) {
            return new ArrayList<>(0);
        }

        try {

            //类似于group by（）
            AggregationGroupByResult groupByResult = buildAggregationGroupBy(param, groupBys);

            //对group by 后的字段加上聚合命令
            List<AggregationBuilder> normalBuilders = buildAggregationParam(param,
                    null, groupByResult);

            if (groupByResult.getAggregationBuilderOriginal() != null) {

                if (groupByResult.getAggregationBuilderNextNotNested() != null) {
                    for (AggregationBuilder child : normalBuilders) {
                        groupByResult.getAggregationBuilderNextNotNested().subAggregation(child);
                    }
                } else {
                    for (AggregationBuilder child : normalBuilders) {
                        groupByResult.getAggregationBuilderOriginal().subAggregation(child);
                    }
                }
                return Arrays.asList(groupByResult.getAggregationBuilderOriginal());
            } else {
                return normalBuilders;
            }

//      //组合聚合字段和groupby字段
//      if (groupByResult.getAggregationBuilderNextNotNested() != null) {
//        for (AbstractAggregationBuilder child : childAggregationBuilders) {
//          groupByResult.getAggregationBuilderNextNotNested().subAggregation(child);
//        }
//        builders.add(groupByResult.getAggregationBuilderOriginal());
//      } else {
//        builders.addAll(childAggregationBuilders);
//      }

        } catch (Exception e) {
            log.error("封装聚合参数失败,{},{}", param, groupBys, e);
        }

        return builders;
    }

    public static AggregationGroupByResult buildAggregationGroupBy(EsModel param,
                                                                   String... groupBys) throws NoSuchFieldException, IllegalAccessException {

        /**Q:为什么要定义groupByResult里这三个参数呢？（狗日的Nested类型贼特殊）
         * Es语法，nested聚合层 只能聚合nested数据中的属性,非nested数据不可放在nested聚合层,例子↓
         * GET order/order_header/_search
         * {
         *   "aggregations": {
         *   "order_item": {nested聚合层
         *     "nested": {
         *       "path": "order_item"
         *     },
         *     "aggregations": {
         *       "order_item.product_id": {nested数据
         *         "terms": {
         *           "field": "order_item.product_id"
         *         },
         *         "aggregations": {
         *           "order_item.quantity": {nested数据
         *             "sum": {
         *               "field": "order_item.quantity"
         *             }
         *           }
         *         }
         *       }
         *     }
         *   },
         *   "order_date": {//非nested数据
         *     "max": {
         *       "field": "order_date"
         *     }
         *   }
         * }
         * }*/
        AggregationGroupByResult groupByResult = new AggregationGroupByResult();
        if (groupBys != null && groupBys.length != 0) {
            Class modelClass = param.getClass();
            for (String groupBy : groupBys) {
                //Nested的类型该方法目前只支持两层，后续支持多层改这里变成递归
                String[] groupBySplit = groupBy.split("\\.");
                Field field = modelClass.getDeclaredField(HumpTool.lineToHump(groupBySplit[0]));
                field.setAccessible(true);
                EsField esField = field.getAnnotation(EsField.class);
                if (groupBySplit.length != 1 && esField.fieldType() == EsFieldTypeEnum.NESTED) {
                    AggregationBuilder nestedBuilder = AggregationBuilders.nested(groupBySplit[0], groupBySplit[0]);
                    AggregationBuilder nestedChildBuilder = AggregationBuilders.terms(groupBy)
                            .field(groupBy);
                    nestedBuilder.subAggregation(nestedChildBuilder);

//          Object nestedObject = field.get(param);
//          if (null != nestedObject){
//            List nestedObjectList = new ArrayList((List)nestedObject);
//            Object fieldValue = nestedObjectList.get(0);
//            List<AbstractAggregationBuilder> AbstractAggregationBuilders = buildAggregationParam(
//                fieldValue, groupBySplit[0]);
//            if (CollectionUtils.isNotEmpty(AbstractAggregationBuilders)) {
//              for (AbstractAggregationBuilder childBuilder : AbstractAggregationBuilders) {
//                nestedChildBuilder.subAggregation(childBuilder);
//              }
//            }
//          }
                    if (groupByResult.getAggregationBuilderOriginal() == null) {
                        groupByResult.setAggregationBuilderOriginal(nestedBuilder);
                        groupByResult.setAggregationBuilderNextNested(nestedChildBuilder);
                        groupByResult.setAggregationBuilderNext(nestedChildBuilder);
                    } else {
                        groupByResult.getAggregationBuilderNext().subAggregation(nestedBuilder);
                        groupByResult.setAggregationBuilderNextNested(nestedChildBuilder);
                        groupByResult.setAggregationBuilderNext(nestedChildBuilder);
                    }
                } else {
                    AggregationBuilder aggregationBuilder = AggregationBuilders.terms(groupBy).field(groupBy);
                    if (groupByResult.getAggregationBuilderOriginal() == null) {
                        groupByResult.setAggregationBuilderOriginal(aggregationBuilder);
                        groupByResult.setAggregationBuilderNext(aggregationBuilder);
                        groupByResult.setAggregationBuilderNextNotNested(aggregationBuilder);
                    } else {
                        groupByResult.getAggregationBuilderNext().subAggregation(aggregationBuilder);
                        groupByResult.setAggregationBuilderNext(aggregationBuilder);
                        groupByResult.setAggregationBuilderNextNotNested(aggregationBuilder);
                    }
                }
            }
        }
        return groupByResult;
    }

    private static List<AggregationBuilder> buildAggregationParam(Object param,
                                                                  String prefix, AggregationGroupByResult groupByResult) throws Exception {
        //获取子父类field
        List<Field> fields = new ArrayList<>(26);
        Class clazz = param.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        //根据属性封装queryBuild
        List<AggregationBuilder> normalBuilders = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            EsField esField = field.getAnnotation(EsField.class);

            //字段Es类型
            EsFieldTypeEnum fieldType = esField.fieldType();
            String fieldName = prefix == null ? HumpTool.humpToLine(field.getName())
                    : prefix + "." + HumpTool.humpToLine(field.getName());

            //字段值
            Object fieldValue = null;
            try {
                fieldValue = field.get(param);
            } catch (IllegalAccessException e) {
                log.error("参数错误,{}", param, JSON.toJSONString(field), e);
                return new ArrayList<>(0);
            }
            if (fieldValue == null) {
                continue;
            }

            //聚合类字段都抽象成EsAggregationsParam
            Class fieldClass = fieldValue.getClass();
            EsAggregationsOperateEnum operate = null;

            if (fieldType == EsFieldTypeEnum.NESTED) {
                if (null != fieldValue) {
                    List nestedObjectList = new ArrayList((List) fieldValue);
                    fieldValue = nestedObjectList.get(0);
                    List<AggregationBuilder> operateResultNested = buildAggregationParam(
                            fieldValue, fieldName, groupByResult);
                    if (CollectionUtils.isNotEmpty(operateResultNested)) {
                        AggregationBuilder aggregationBuilderNextNested = groupByResult
                                .getAggregationBuilderNextNested();
                        if (aggregationBuilderNextNested == null) {
                            log.error("Nested的类型目前暂时只能配合groupBy使用,{}", param);
                            throw new Exception("Nested的类型目前暂时只能配合groupBy使用");
                        }
                        for (AggregationBuilder childBuilder : operateResultNested) {
                            aggregationBuilderNextNested.subAggregation(childBuilder);
                        }
                    }
                }
                continue;
            }

            if (fieldClass == EsAggregationsParam.class) {
                operate = ((EsAggregationsParam) fieldValue).getOperate();
                normalBuilders.addAll(
                        buildAggregationsParamExcutor(fieldValue, fieldName, fieldType, operate,
                                groupByResult));
            } else if (fieldClass.isArray()
                    && fieldClass.getComponentType() == EsAggregationsParam.class) {
                for (EsAggregationsParam esParam : (EsAggregationsParam[]) fieldValue) {
                    operate = esParam.getOperate();
                    normalBuilders.addAll(
                            buildAggregationsParamExcutor(esParam, fieldName, fieldType, operate, groupByResult));
                }
            } else {
                normalBuilders.addAll(
                        buildAggregationsParamExcutor(fieldValue, fieldName, fieldType, operate,
                                groupByResult));
            }
        }

        return normalBuilders;
    }


    private static List<AggregationBuilder> buildAggregationsParamExcutor(
            Object fieldValue, String fieldName, EsFieldTypeEnum fieldType,
            EsAggregationsOperateEnum operate, AggregationGroupByResult groupByResult) throws Exception {

        switch (fieldType) {
            case OBJECT:
                return buildAggregationParam(fieldValue, fieldName, groupByResult);
            case NESTED:
                ArrayList nestedObjectList = new ArrayList((List) fieldValue);
                fieldValue = nestedObjectList.get(0);
                NestedAggregationBuilder nestedBuilder = AggregationBuilders.nested(fieldName, fieldName);
                List<AggregationBuilder> AbstractAggregationBuilders = buildAggregationParam(
                        fieldValue, fieldName, groupByResult);
                if (CollectionUtils.isNotEmpty(AbstractAggregationBuilders)) {
                    for (AggregationBuilder childBuilder : AbstractAggregationBuilders) {
                        nestedBuilder.subAggregation(childBuilder);
                    }
                }
                return Arrays.asList(nestedBuilder);
            default:
                AggregationBuilder aggregationBuilder = buildAggregationByOperate(operate,
                        fieldName);
                if (aggregationBuilder == null) {
                    return new ArrayList<>(0);
                } else {
                    return Arrays.asList(aggregationBuilder);
                }
        }
    }

    private static AggregationBuilder buildAggregationByOperate(
            EsAggregationsOperateEnum operate, String fieldName) {
        if (operate == null) {
            return null;
        }
        switch (operate) {
            case MIN:
                return AggregationBuilders.min(fieldName).field(fieldName);
            case MAX:
                return AggregationBuilders.max(fieldName).field(fieldName);
            case AVG:
                return AggregationBuilders.avg(fieldName).field(fieldName);
            case SUM:
                return AggregationBuilders.sum(fieldName).field(fieldName);
            default:
                return null;
        }
    }


}
