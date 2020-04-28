package com.lgy.canal.constant;

/**
 * @Description 常量订单字段
 * @Author LGy
 * @Date 2020/4/23
 */
public class EsOrderFieldConstant {



    /**
     * 默认地，Elasticsearch 在 _source 字段存储代表文档体的JSON字符串。和所有被存储的字段一样， _source 字段在被写入磁盘之前先会被压缩。
     * 这个字段的存储几乎总是我们想要的，因为它意味着下面的这些：
     * 搜索结果包括了整个可用的文档——不需要额外的从另一个的数据仓库来取文档。
     * 如果没有 _source 字段，部分 update 请求不会生效。
     * 当你的映射改变时，你需要重新索引你的数据，有了_source字段你可以直接从Elasticsearch这样做，而不必从另一个（通常是速度更慢的）数据仓库取回你的所有文档。
     * 当你不需要看到整个文档时，单个字段可以从 _source 字段提取和通过 get 或者 search 请求返回。
     * 调试查询语句更加简单，因为你可以直接看到每个文档包括什么，而不是从一列id猜测它们的内容。
     */
    public static final String ES_SOURCE = "_source";

    /**
     * 订单编码
     */
    public static final String ORDER_ID = "order_id";


    public static final String PARAM_NAME = "name";

    public static final String PARAM_VALUE = "value";


    public static final String EMPTY_JSON = "{}";

    public static final int MAX_QUEUE_SIZE = 40960;



}
