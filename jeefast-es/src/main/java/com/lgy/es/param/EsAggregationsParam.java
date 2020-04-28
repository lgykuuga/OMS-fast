package com.lgy.es.param;

import com.lgy.es.enums.EsAggregationsOperateEnum;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * @author:D.J
 * @date: 2019-07-10
 **/
@Data
@Accessors(chain = true)
public class EsAggregationsParam {

  @NonNull
  private EsAggregationsOperateEnum operate;

  public EsAggregationsParam(EsAggregationsOperateEnum operate) {
    this.operate = operate;
  }
}
