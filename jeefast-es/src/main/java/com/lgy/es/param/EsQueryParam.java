package com.lgy.es.param;

import com.lgy.es.enums.EsQueryOperateEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author:D.J
 * @date: 2019-07-10
 **/
@Data
@Accessors(chain = true)
public class EsQueryParam {

    /**
     * 操作
     */
    private EsQueryOperateEnum operate;

    /**
     * 参数
     */
    private Object param;

    public EsQueryParam(EsQueryOperateEnum operate, Object param) {
        this.operate = operate;
        this.param = param;
    }
}
