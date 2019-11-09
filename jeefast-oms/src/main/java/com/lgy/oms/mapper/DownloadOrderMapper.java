package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.DownloadOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 下载订单 数据层
 *
 * @author lgy
 * @date 2019-10-14
 */
@Mapper
public interface DownloadOrderMapper extends BaseMapper<DownloadOrder> {

    /**
     * 清空下载订单日志
     */
    void cleanDownloadLog();
}