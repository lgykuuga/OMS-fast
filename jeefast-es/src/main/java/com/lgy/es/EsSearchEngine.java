package com.lgy.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgy.es.exception.EsException;
import com.lgy.es.param.EsSort;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Service
@Log4j2
public class EsSearchEngine {

    private static final String INDEX = "oms";

    private static final String TYPE = "order_main";


    public JSONObject query(QueryBuilder queryBuilder, int page, int size, EsSort esSort, String... filedNames)
            throws EsException {
        log.info("es order 查询开始 入参：{}", JSON.toJSONString(queryBuilder));
        String respJson = null;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        try {

            //构建查询
            searchSourceBuilder.query(queryBuilder).from((page - 1) * size).size(size);
            if (filedNames.length != 0) {
                searchSourceBuilder.fetchSource(filedNames, null);
            }
            if (esSort != null) {
                searchSourceBuilder.sort(esSort.getName(), esSort.getOrder());
            }

            //请求
            respJson = requestSearch(searchSourceBuilder.toString());
        } catch (Exception e) {
            log.error("查询ES 服务异常,{}", searchSourceBuilder, e);
            throw new EsException(e);
        }

        if (respJson != null) {
            return JSONObject.parseObject(respJson).getJSONObject("hits");
        }
        return new JSONObject();
    }

    public JSONObject aggregation(QueryBuilder queryBuilder, List<AbstractAggregationBuilder> aggregationBuilders)
            throws EsException {

        String respJson = null;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        try {

            //构建查询
            searchSourceBuilder.query(queryBuilder).fetchSource(false);

            for (AbstractAggregationBuilder aggregationBuilder : aggregationBuilders) {
                searchSourceBuilder.aggregation(aggregationBuilder);
            }

            //请求
            respJson = requestSearch(searchSourceBuilder.toString());

            if (respJson != null) {
                return JSONObject.parseObject(respJson);
            }
        } catch (Exception e) {
            log.error("查询ES 服务异常,{}", searchSourceBuilder, e);
            throw new EsException(e);
        }
        return null;
    }


    private String requestSearch(String esStr) {
        log.info("Es请求参数,{}", esStr);
        long currentTime = System.currentTimeMillis();
        String respJson = "";


        log.info("ES查询用时[{}]s,查询结果:{}", System.currentTimeMillis() - currentTime);
        return respJson;
    }

}
