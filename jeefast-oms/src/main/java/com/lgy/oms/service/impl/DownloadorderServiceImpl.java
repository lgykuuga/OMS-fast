package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.service.business.IRequestRemoteInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.DownloadorderMapper;
import com.lgy.oms.domain.Downloadorder;
import com.lgy.oms.service.IDownloadorderService;

/**
 * 下载订单 服务层实现
 *
 * @author lgy
 * @date 2019-10-14
 */
@Service
public class DownloadorderServiceImpl extends ServiceImpl<DownloadorderMapper, Downloadorder> implements IDownloadorderService {

    @Autowired
    DownloadorderMapper downloadorderMapper;

    @Autowired
    IRequestRemoteInterfaceService requestRemoteInterfaceService;

    @Override
    public CommonResponse<String> downloadByTime(Downloadorder downloadorder) {
        return requestRemoteInterfaceService.getOrderList(downloadorder.getShop(),
                downloadorder.getBedt(), downloadorder.getEndt());
    }

    @Override
    public CommonResponse<String> downloadByTid(String shop, String tids, boolean downloadRefundDetails) {
        return requestRemoteInterfaceService.getOrderDetailsAndSave(shop, tids, downloadRefundDetails);
    }

    @Override
    public void cleanDownloadLog() {
        downloadorderMapper.cleanDownloadLog();
    }
}