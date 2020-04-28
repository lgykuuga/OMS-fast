package com.lgy.es.result;

import lombok.Data;
import lombok.experimental.Accessors;
import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * @Description 汇总结果分组
 * @Author LGy
 * @Date 2020/4/23
 */
@Data
@Accessors(chain = true)
public class AggregationGroupByResult {

    /**
     * 最外层
     */
    AggregationBuilder aggregationBuilderOriginal;
    /**
     * 最后一层
     */
    AggregationBuilder aggregationBuilderNext;
    /**
     * Nested层的最后一层
     */
    AggregationBuilder aggregationBuilderNextNested;
    /**
     * 非Nested层的最后一层
     */
    AggregationBuilder aggregationBuilderNextNotNested;
}
