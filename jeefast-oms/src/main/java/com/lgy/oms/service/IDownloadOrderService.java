package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.DownloadOrder;

/**
 * 下载订单 服务层
 *
 * @author lgy
 * @date 2019-10-14
 */
public interface IDownloadOrderService extends IService<DownloadOrder> {

    /**
     * 根据时间段手动下载订单
     *
     * @param downloadOrder 下载订单请求参数
     * @return
     */
    CommonResponse<String> downloadByTime(DownloadOrder downloadOrder);

    /**
     * 根据单号下载订单
     * @param shop 店铺编码
     * @param tids 多条订单号用','分隔
     * @return
     */
    CommonResponse<String> downloadByTid(String shop, String tids);

    /**
     * 清空订单下载请求日志
     */
    void cleanDownloadLog();
}