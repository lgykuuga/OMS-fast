package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.DownloadOrder;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.enums.strategy.DownloadOrderInterfaceEnum;
import com.lgy.oms.mapper.DownloadOrderMapper;
import com.lgy.oms.service.IDownloadOrderService;
import com.lgy.oms.service.IShopInterfacesService;
import com.lgy.oms.business.IRequestRemoteInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 下载订单 服务层实现
 *
 * @author lgy
 * @date 201-10-14
 */
@Service
public class DownloadOrderServiceImpl extends ServiceImpl<DownloadOrderMapper, DownloadOrder> implements IDownloadOrderService {

    @Resource
    DownloadOrderMapper downloadOrderMapper;

    @Autowired
    IShopInterfacesService shopInterfacesService;

    @Autowired
    IRequestRemoteInterfaceService requestRemoteInterfaceService;

    @Override
    public CommonResponse<String> downloadByTime(DownloadOrder downloadorder) {
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

        } else if (DownloadOrderInterfaceEnum.RDS.name().equals(shopInterfaces.getJklx())) {
            //RDS
            return requestRemoteInterfaceService.getOrderByRDS(shopInterfaces,
                    downloadorder.getBedt(), downloadorder.getEndt());

        } else if (DownloadOrderInterfaceEnum.DIRECT.name().equals(shopInterfaces.getJklx())) {
            //TODO 直接对接平台
            return new CommonResponse<String>().error(Constants.FAIL, "直接对接平台未开发,敬请期待");
        }

        return new CommonResponse<String>().error(Constants.FAIL, "没有维护店铺接口类型");
    }

    @Override
    public CommonResponse<String> downloadByTid(String shop, String tids) {
        //获取店铺接口设置
        QueryWrapper<ShopInterfaces> wrapper = new QueryWrapper<>();
        wrapper.eq("shop", shop);
        ShopInterfaces shopInterfaces = shopInterfacesService.getOne(wrapper);

        if (shopInterfaces == null || StringUtils.isEmpty(shopInterfaces.getSurl())) {
            return new CommonResponse<String>().error(Constants.FAIL, "系统设置中店铺编码不正确或没有维护店铺接口地址");
        }

        if (DownloadOrderInterfaceEnum.GODS.name().equals(shopInterfaces.getJklx())
            || DownloadOrderInterfaceEnum.RDS.name().equals(shopInterfaces.getJklx())) {
            //GODS || RDS
            return requestRemoteInterfaceService.getOrderDetailsAndSave(shopInterfaces, tids);
        }

        if (DownloadOrderInterfaceEnum.KJY.name().equals(shopInterfaces.getJklx())) {
            //跨境翼
            return new CommonResponse<String>().error(Constants.FAIL, "跨境翼不支持根据单号下载订单");
        }

        if (DownloadOrderInterfaceEnum.DIRECT.name().equals(shopInterfaces.getJklx())) {
            //TODO 直接对接平台
            return new CommonResponse<String>().error(Constants.FAIL, "直接对接平台未开发,敬请期待");
        }

        return new CommonResponse<String>().error(Constants.FAIL, "没有维护店铺接口类型");
    }

    @Override
    public void cleanDownloadLog() {
        downloadOrderMapper.cleanDownloadLog();
    }
}