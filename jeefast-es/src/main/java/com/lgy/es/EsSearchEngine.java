package com.lgy.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgy.common.utils.StringUtils;
import com.lgy.es.exception.EsException;
import com.lgy.es.param.EsSort;
import lombok.extern.log4j.Log4j2;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Service
@Log4j2
public class EsSearchEngine {

    private static final String SEARCH = "/_search";

    private static final String UPDATE = "/_update";

    private static final String POST = "POST";

    private static final String GET = "GET";

    private static final String PUT = "PUT";

    private static final String DELETE = "DELETE";

    private static final String DEFAULT_ROUTER = "/order/order_header";

    private static final String TYPE = "order_main";

    private static final String RETRY_TIME = "?retry_on_conflict=10";

    @Autowired
    TransportClient transportClient;

    @Value("${elasticsearch.index}")
    protected String index;

    public JSONObject query(QueryBuilder queryBuilder, int page, int size, EsSort esSort, String... filedNames)
            throws EsException {
        log.info("es order 查询开始 入参：{}", JSON.toJSONString(queryBuilder));
        String respJson;
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

            //请求操作
            respJson = request(SEARCH, searchSourceBuilder.toString());
        } catch (Exception e) {
            log.error("查询ES 服务异常,{}", searchSourceBuilder, e);
            throw new EsException(e);
        }

        if (StringUtils.isNotBlank(respJson)) {
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


    private String request(String operate, String esStr) throws IOException {
        log.info("es请求订单操作{}参数:{}", operate, esStr);
        long currentTime = System.currentTimeMillis();
        String respJson;

        // 发起请求得到响应
        GetResponse response = transportClient.prepareGet(index, TYPE,"10").get();
        System.out.println(response.getSource());

        Response resp = restClient
                .performRequest(POST, router, Collections.<String, String>emptyMap(),
                        new StringEntity(esStr, ContentType.APPLICATION_JSON));
        respJson = EntityUtils.toString(resp.getEntity(), "UTF-8");
        log.info("ES查询用时,{},查询结果,{}", System.currentTimeMillis() - currentTime, respJson);
        return respJson;
    }

}
