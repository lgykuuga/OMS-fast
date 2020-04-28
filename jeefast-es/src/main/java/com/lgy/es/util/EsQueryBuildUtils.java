package com.lgy.es.util;

import com.alibaba.fastjson.JSON;
import com.lgy.es.annotation.EsField;
import com.lgy.es.enums.EsFieldTypeEnum;
import com.lgy.es.enums.EsQueryOperateEnum;
import com.lgy.es.param.EsQueryParam;
import com.lgy.es.param.EsRange;
import com.lgy.es.common.EsModel;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Log4j2
public class EsQueryBuildUtils {

    /**
     * @doc:这个方法在构造查询器的时候结合EsParam类使用，参数必须继承EsModel 首先 EsOrderHeader param = new EsOrderHeader();
     * 例1：单个查询：查询订单状态为"ORDER_WAIT_PAYED"的订单
     * param.setStatus(new EsQueryParam(EsQueryOperateEnum.MUST,"ORDER_WAIT_PAYED"));
     * 或(简写)
     * param.setStatus("ORDER_WAIT_PAYED"); //不用 EsQueryParam 时默认MUST操作
     * 例2：集合查询：查询订单状态为"ORDER_WAIT_PAYED"和"ORDER_CANCELLED"的订单
     * param.setStatus(new EsQueryParam(EsQueryOperateEnum.MUST,{"ORDER_WAIT_PAYED","ORDER_CANCELLED"}));
     * 或(简写)
     * param.setStatus({"ORDER_WAIT_PAYED","ORDER_CANCELLED"}) //这里相当于数据库中的in查询
     * 例3：多重条件查询：查询订单状态为"ORDER_WAIT_PAYED"且不为"ORDER_CANCELLED"的订单
     * EsQueryParam esParam1 = new EsQueryParam(EsQueryOperateEnum.MUST,ORDER_WAIT_PAYED);
     * EsQueryParam esParam2 = new EsQueryParam(EsQueryOperateEnum.MUST_NOT,ORDER_CANCELLED);
     * param.setStatus({esParam1,esParam2});
     */
    public static QueryBuilder buildSimpleQuery(EsModel param) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        try {
            buildQueryParam(param, null, queryBuilder);
        } catch (IllegalAccessException e) {
            log.error("ES 转换参数失败,{}", JSON.toJSONString(param), e);
        }

        return queryBuilder;
    }

    private static void buildQueryParam(Object objectParam, String prefix, BoolQueryBuilder queryBuilder)
            throws IllegalAccessException {

        //获取子父类field
        List<Field> fields = new ArrayList<>(26);
        Class clazz = objectParam.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        //根据属性封装queryBuild
        for (Field field : fields) {
            field.setAccessible(true);
            EsField esField = field.getAnnotation(EsField.class);

            //字段Es类型
            EsFieldTypeEnum fieldType = esField.fieldType();
            String fieldName = prefix == null ? HumpTool.humpToLine(field.getName())
                    : prefix + "." + HumpTool.humpToLine(field.getName());


            //字段值
            Object fieldValue = field.get(objectParam);
            if (fieldValue == null) {
                continue;
            }

            //检索类字段都抽象成EsQueryParam，EsQueryParam包含操作和字段值
            Class fieldClass = fieldValue.getClass();
            EsQueryOperateEnum operate = null;
            if (fieldClass == EsQueryParam.class) {
                operate = ((EsQueryParam) fieldValue).getOperate();
                fieldValue = ((EsQueryParam) fieldValue).getParam();
                buildQueryParamExcutor(queryBuilder, fieldType, fieldName, fieldValue, operate);
            } else if (fieldClass.isArray() && fieldClass.getComponentType() == EsQueryParam.class) {
                for (EsQueryParam esQueryParam : (EsQueryParam[]) fieldValue) {
                    if (esQueryParam == null) {
                        continue;
                    }
                    operate = esQueryParam.getOperate();
                    fieldValue = esQueryParam.getParam();
                    buildQueryParamExcutor(queryBuilder, fieldType, fieldName, fieldValue, operate);
                }
            } else {
                buildQueryParamExcutor(queryBuilder, fieldType, fieldName, fieldValue, operate);
            }
        }
    }

    private static void buildQueryParamExcutor(BoolQueryBuilder queryBuilder,
                                               EsFieldTypeEnum fieldType, String fieldName, Object fieldValue, EsQueryOperateEnum operate)
            throws IllegalAccessException {

        if (fieldValue == null && operate == null) {
            return;
        } else if (fieldValue == null && operate == EsQueryOperateEnum.EXISTS) {
            buildQueryByOperate(queryBuilder, operate, QueryBuilders.existsQuery(fieldName));
            return;
        }

        switch (fieldType) {
            case TEXT:
                QueryBuilder textQueryBuilder = null;
                if (fieldValue instanceof EsRange) {
                    textQueryBuilder = QueryBuilders.rangeQuery(fieldName);
                    if (((EsRange) fieldValue).getMin() != null) {
                        ((RangeQueryBuilder) textQueryBuilder).gte(((EsRange) fieldValue).getMin());
                    }
                    if (((EsRange) fieldValue).getMax() != null) {
                        ((RangeQueryBuilder) textQueryBuilder).lte(((EsRange) fieldValue).getMax());
                    }
                } else {
                    textQueryBuilder = QueryBuilders.matchPhraseQuery(fieldName, fieldValue);
                }
                buildQueryByOperate(queryBuilder, operate, textQueryBuilder);
                break;
            case KEYWORD:
            case DATE:
            case BOOL:
                QueryBuilder keyQueryBuilder = null;
                if (fieldValue instanceof Collection) {
                    keyQueryBuilder = QueryBuilders.termsQuery(fieldName, ((Collection) fieldValue).toArray());
                } else if (fieldValue instanceof EsRange) {
                    keyQueryBuilder = QueryBuilders.rangeQuery(fieldName);
                    if (((EsRange) fieldValue).getMin() != null) {
                        ((RangeQueryBuilder) keyQueryBuilder).gte(converterRange(((EsRange) fieldValue).getMin()));
                    }
                    if (((EsRange) fieldValue).getMax() != null) {
                        ((RangeQueryBuilder) keyQueryBuilder).lte(converterRange(((EsRange) fieldValue).getMax()));
                    }
                } else {
                    keyQueryBuilder = QueryBuilders.termQuery(fieldName, fieldValue);
                }
                buildQueryByOperate(queryBuilder, operate, keyQueryBuilder);
                break;
            case OBJECT:
                buildQueryParam(fieldValue, fieldName, queryBuilder);
                break;
            case NESTED:
                BoolQueryBuilder boolQueryBuilder4Nested = QueryBuilders.boolQuery();
                ArrayList nestedObjectList = new ArrayList((List) fieldValue);
                for (Object item : nestedObjectList) {
                    buildQueryParam(item, fieldName, boolQueryBuilder4Nested);
                }
                if (boolQueryBuilder4Nested.hasClauses()) {
                    NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(fieldName, boolQueryBuilder4Nested, ScoreMode.None);
                    queryBuilder.must(nestedQueryBuilder);
                }
                break;
            default:
                break;
        }
    }

    private static void buildQueryByOperate(BoolQueryBuilder queryBuilder, EsQueryOperateEnum operate,
                                            QueryBuilder queryBuilderChild) {
        if (operate == null) {
            operate = EsQueryOperateEnum.MUST;
        }
        switch (operate) {
            case EXISTS:
                queryBuilder.filter(queryBuilderChild);
            case SHOULD:
                queryBuilder.should(queryBuilderChild);
                break;
            case MUST_NOT:
                queryBuilder.mustNot(queryBuilderChild);
                break;
            default:
                queryBuilder.must(queryBuilderChild);
                break;
        }
    }

    private static Object converterRange(Object object) {
        if (object.getClass() == Date.class) {
            DateFormat bf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+0800");
            return bf.format((Date) object);
        }
        return object;
    }

    public static QueryBuilder buildSimpleQueryByOR(EsModel... params) {

        BoolQueryBuilder queryBuilderFather = QueryBuilders.boolQuery();
        for (EsModel param : params) {
            try {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                buildQueryParam(param, null, queryBuilder);
                queryBuilderFather.should(queryBuilder);
            } catch (IllegalAccessException e) {
                log.error("ES 转换参数失败,{}", JSON.toJSONString(param), e);
            }
        }
        return queryBuilderFather;
    }


}
