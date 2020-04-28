package com.lgy.es.param;

import lombok.Data;
import lombok.experimental.Accessors;
import org.elasticsearch.search.sort.SortOrder;


/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Data
@Accessors(chain = true)
public class EsSort {

    String name;

    SortOrder order;

}
