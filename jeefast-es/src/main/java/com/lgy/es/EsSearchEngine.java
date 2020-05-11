package com.lgy.es;

import com.alibaba.fastjson.JSONObject;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.constant.EsOrderFieldConstant;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.model.EsPage;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @Description
 * @Author LGy
 * @Date 2020/5/6
 */
@Service
@Log4j2
public class EsSearchEngine {

    @Autowired
    TransportClient transportClient;

    /**
     * 创建索引
     *
     * @param index 索引ID
     * @return 执行结果
     */
    public CommonResponse<String> createIndex(String index) {
        if (isIndexExist(index)) {
            return new CommonResponse<String>().error(Constants.FAIL, "索引已存在");
        }
        CreateIndexResponse indexResponse = transportClient.admin().indices().prepareCreate(index).execute().actionGet();
        log.info("用户[{}]创建索引[{}]执行成功?[{}]", ShiroUtils.getSysUser().getUserId(), index, indexResponse.isAcknowledged());
        if (indexResponse.isAcknowledged()) {
            return new CommonResponse<String>().ok("索引创建成功");
        }
        return new CommonResponse<String>().error(Constants.FAIL, "索引创建失败");
    }

    /**
     * 删除索引
     *
     * @param index 索引ID
     * @return 执行结果
     */
    public CommonResponse<String> deleteIndex(String index) {
        if (!isIndexExist(index)) {
            return new CommonResponse<String>().error(Constants.FAIL, "索引不存在");
        }
        AcknowledgedResponse dResponse = transportClient.admin().indices().prepareDelete(index).execute().actionGet();
        log.info("用户[{}]删除索引[{}]执行成功?[{}]", ShiroUtils.getSysUser().getUserId(), index, dResponse.isAcknowledged());
        if (dResponse.isAcknowledged()) {
            return new CommonResponse<String>().ok("索引删除成功");
        }
        return new CommonResponse<String>().error(Constants.FAIL, "索引删除失败");
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonObject 要增加的数据
     * @param index      索引, 类似数据库
     * @param type       类型, 类似表
     * @param id         数据ID
     * @return 执行结果
     */
    protected CommonResponse<String> addData(JSONObject jsonObject, String index, String type, String id) {

        IndexResponse response = transportClient.prepareIndex(index, type, id).setSource(jsonObject).get();

        log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());

        if (Objects.equals(RestStatus.OK.getStatus(), response.status().getStatus())) {
            return new CommonResponse<String>().ok(response.getId());
        }
        return new CommonResponse<String>().error(response.status().getStatus() + "", response.getId());
    }


    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     * @return 执行结果
     */
    protected CommonResponse<String> deleteDataById(String index, String type, String id) {

        DeleteResponse response = transportClient.prepareDelete(index, type, id).execute().actionGet();

        log.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());

        if (Objects.equals(RestStatus.OK.getStatus(), response.status().getStatus())) {
            return new CommonResponse<String>().ok(response.getId());
        }

        return new CommonResponse<String>().error(response.status().getStatus() + "", response.getId());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     */
    protected void updateDataById(JSONObject jsonObject, String index, String type, String id) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(type).id(id).doc(jsonObject);
        transportClient.update(updateRequest);
    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return 执行结果
     */
    protected Map<String, Object> searchDataById(String index, String type, String id, String fields) {
        GetRequestBuilder getRequestBuilder = transportClient.prepareGet(index, type, id);
        if (StringUtils.isNotEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }
        GetResponse getResponse = getRequestBuilder.execute().actionGet();
        return getResponse.getSource();
    }

    /**
     * 批量插入
     */
    protected void bulkInsert(List<Map> orders, String index, String type) {
        BulkRequestBuilder bulk = transportClient.prepareBulk();
        orders.forEach(order -> {
            String orderId = (String) order.get(EsOrderFieldConstant.ORDER_ID);
            bulk.add(transportClient.prepareIndex(index, type, orderId).setSource(order));
        });
        BulkResponse responses = bulk.get();
        log.info("bulk response {}", responses.status().name());
    }

    /**
     * 使用分词查询,并分页
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param startPage      当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return 执行结果
     */
    protected EsPage searchDataPage(String index, String type, int startPage, int pageSize, QueryBuilder query,
                                    String fields, String sortField, String highlightField) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index);

        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(Constants.COMMA));
        }

        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(Constants.COMMA), null);
        }

        //排序字段
        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        // 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            //highlightBuilder.preTags("<span style='color:red' >");//设置前缀
            //highlightBuilder.postTags("</span>");//设置后缀

            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(query);

        // 分页应用
        searchRequestBuilder.setFrom(startPage).setSize(pageSize);

        //设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        log.info("分页查询\n{}", searchRequestBuilder);

        // 执行搜索,返回搜索响应信息
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().getTotalHits();
        long length = searchResponse.getHits().getHits().length;

        log.debug("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (Objects.equals(RestStatus.OK.getStatus(), searchResponse.status().getStatus())) {
            // 解析对象
            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);

            return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
        }

        return null;
    }

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse 查询返回结果集
     * @param highlightField 高亮字段
     */
    private List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());

            if (StringUtils.isNotEmpty(highlightField)) {

                System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        sb.append(str.string());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, sb.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }
        return sourceList;
    }

    /**
     * 判断索引是否存在
     *
     * @param index 索引名称
     * @return 是否存在索引
     */
    private boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = transportClient.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        return inExistsResponse.isExists();
    }

}
