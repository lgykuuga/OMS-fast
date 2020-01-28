package com.lgy.oms.mapper;

import com.lgy.oms.domain.StrategyConvert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.vo.StrategyConvertVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 转单策略 数据层
 *
 * @author lgy
 * @date 2019-10-31
 */
public interface StrategyConvertMapper extends BaseMapper<StrategyConvert> {

    /**
     * 查询list
     * @param strategyConvertVO 请求VO对象
     * @return list
     */
    List<StrategyConvertVO> queryList(@Param("strategyConvertVO")StrategyConvertVO strategyConvertVO);
}