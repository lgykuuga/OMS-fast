package com.lgy.oms.mapper;

import com.lgy.oms.domain.Downloadorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 下载订单 数据层
 *
 * @author lgy
 * @date 2019-10-14
 */
public interface DownloadorderMapper extends BaseMapper<Downloadorder> {

    /**
     * 清空下载订单日志
     */
    void cleanDownloadLog();
}