package com.lgy.biz.distribution;

import com.alibaba.fastjson.JSONObject;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.es.EsSearchEngine;
import com.lgy.model.EsPage;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;
import java.util.Map;

/**
 * @Description 配货订单搜索
 * @Author LGy
 * @Date 2020/5/8 14:16
 **/
public class DistributionEsSearchEngine extends EsSearchEngine {

    private static final String INDEX = "distribution";

    private static final String TYPE = "distribution";

    /**
     * 通过ID获取数据
     *
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return 执行结果
     */
    public Map<String, Object> searchDataById(String id, String fields) {
        return searchDataById(INDEX, TYPE, id, fields);
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonObject 要增加的数据
     * @param id         数据ID
     * @return 执行结果
     */
    public CommonResponse<String> addData(JSONObject jsonObject, String id) {
        return addData(jsonObject, INDEX, TYPE, id);
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param id         数据ID
     */
    public void updateDataById(JSONObject jsonObject, String id) {
        updateDataById(jsonObject, INDEX, TYPE, id);
    }

    /**
     * 通过ID删除数据
     *
     * @param id 数据ID
     * @return 执行结果
     */
    public CommonResponse<String> deleteDataById(String id) {
        return deleteDataById(INDEX, TYPE, id);
    }

    /**
     * 使用分词查询,并分页
     *
     * @param startPage      当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return 执行结果
     */
    public EsPage searchDataPage(int startPage, int pageSize, QueryBuilder query,
                                 String fields, String sortField, String highlightField) {
        return searchDataPage(INDEX, TYPE, startPage, pageSize, query, fields, sortField, highlightField);
    }

    /**
     * 批量插入
     *
     * @param orders 订单信息
     */
    public void bulkInsert(List<Map> orders) {
        bulkInsert(orders, INDEX, TYPE);
    }

}
