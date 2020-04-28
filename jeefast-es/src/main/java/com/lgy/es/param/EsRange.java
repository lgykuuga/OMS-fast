package com.lgy.es.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
@Data
@Accessors(chain = true)
public class EsRange {

    /**
     * 必须是Date
     */
    private Object min;

    /**
     * 必须是Date
     */
    private Object max;
}
