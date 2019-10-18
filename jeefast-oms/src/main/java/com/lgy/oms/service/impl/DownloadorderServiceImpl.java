package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.Downloadorder;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.enums.DownloadOrderInterfaceEnum;
import com.lgy.oms.mapper.DownloadorderMapper;
import com.lgy.oms.service.IDownloadorderService;
import com.lgy.oms.service.IShopInterfacesService;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.business.IRequestRemoteInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 下载订单 服务层实现
 *
 * @author lgy
 * @date 2019-10-14
 */
@Service
public class DownloadorderServiceImpl extends ServiceImpl<DownloadorderMapper, Downloadorder> implements IDownloadorderService {

    @Autowired
    DownloadorderMapper downloadOrdrMapper;

    @Autowired
    IShopInterfacesService shopInterfacesService;

    @Autowired
    IRequestRemoteInterfaceService requestRemoteInterfaceService;

    @Override
    public CommonResponse<String> downloadByTime(Downloadorder downloadorder) {
        //获取店铺接口设置
        QueryWrapper<ShopInterfaces> wrapper = new QueryWrapper<>();
        wrapper.eq("shop", downloadorder.getShop());
        ShopInterfaces shopInterfaces = shopInterfacesService.getOne(wrapper);

        if (shopInterfaces == null || StringUtils.isEmpty(shopInterfaces.getSurl())) {
            return new CommonResponse<String>().error(Constants.FAIL, "系统设置中店铺编码不正确或没有维护店铺接口地址");
        }

        if (DownloadOrderInterfaceEnum.KJY.name().equals(shopInterfaces.getJklx())) {
            //跨境翼
            return requestRemoteInterfaceService.getOrderDetailsByTime(shopInterfaces,
                    downloadorder.getBedt(), downloadorder.getEndt());

        } else if (DownloadOrderInterfaceEnum.GODS.name().equals(shopInterfaces.getJklx())) {
            //GODS
            return requestRemoteInterfaceService.getOrderListByTime(shopInterfaces,
                    downloadorder.getBedt(), downloadorder.getEndt());

        } else if (DownloadOrderInterfaceEnum.DIRECT.name().equals(shopInterfaces.getJklx())) {
            //TODO 直接对接平台
            return new CommonResponse<String>().error(Constants.FAIL, "直接对接平台未开发,敬请期待");
        }

        return new CommonResponse<String>().error(Constants.FAIL, "没有维护店铺接口类型");
    }

    @Override
    public CommonResponse<String> downloadByTid(String shop, String tids, boolean downloadRefundDetails) {
        //获取店铺接口设置
        QueryWrapper<ShopInterfaces> wrapper = new QueryWrapper<>();
        wrapper.eq("shop", shop);
        ShopInterfaces shopInterfaces = shopInterfacesService.getOne(wrapper);

        if (shopInterfaces == null || StringUtils.isEmpty(shopInterfaces.getSurl())) {
            return new CommonResponse<String>().error(Constants.FAIL, "系统设置中店铺编码不正确或没有维护店铺接口地址");
        }

        if (DownloadOrderInterfaceEnum.DIRECT.name().equals(shopInterfaces.getJklx())) {
            //TODO 直接对接平台
            return new CommonResponse<String>().error(Constants.FAIL, "直接对接平台未开发,敬请期待");

        } else if (DownloadOrderInterfaceEnum.GODS.name().equals(shopInterfaces.getJklx())) {
            //GODS
            return requestRemoteInterfaceService.getOrderDetailsAndSave(shopInterfaces, tids);

        } else if (DownloadOrderInterfaceEnum.KJY.name().equals(shopInterfaces.getJklx())) {
            //跨境翼
            return new CommonResponse<String>().error(Constants.FAIL, "跨境翼不支持根据单号下载订单");
        }

        return new CommonResponse<String>().error(Constants.FAIL, "没有维护店铺接口类型");
    }

    @Override
    public void cleanDownloadLog() {
        downloadOrdrMapper.cleanDownloadLog();
    }
}