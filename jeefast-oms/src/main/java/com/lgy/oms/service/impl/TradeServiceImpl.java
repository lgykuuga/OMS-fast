package com.lgy.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.constant.ResponseCode;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.DateUtils;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.StandardOrderData;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.enums.order.TradeTranformStatusEnum;
import com.lgy.oms.enums.strategy.DownloadOrderInterfaceEnum;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.interfaces.kjy.bean.KjyTrade;
import com.lgy.oms.interfaces.kjy.util.KjyConvert;
import com.lgy.oms.interfaces.rds.bean.RdsTradeMain;
import com.lgy.oms.interfaces.rds.service.IJdpTbTradeService;
import com.lgy.oms.interfaces.rds.util.RdsConvert;
import com.lgy.oms.mapper.TradeMapper;
import com.lgy.oms.service.IShopInterfacesService;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.ITradeStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易订单 服务层实现
 *
 * @author lgy
 * @date 2019-10-15
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements ITradeService {

    @Resource
    TradeMapper tradeMapper;
    /** 标准订单报文快照 */
    @Autowired
    ITradeStandardService tradeStandardService;
    /** 店铺接口信息 */
    @Autowired
    IShopInterfacesService shopInterfacesService;

    @Autowired
    IJdpTbTradeService jdpTbTradeService;

    @Override
    public Trade checkOrderExist(String tid, String shop, boolean valid) {

        List<Trade> trades = tradeMapper.checkOrderExist(tid, shop, valid);
        if (trades != null && !trades.isEmpty()) {
            return trades.get(0);
        }
        return null;
    }

    @Override
    public String previewOrder(String tid, String type) {
        // 查询表信息
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        Trade trade = tradeMapper.selectOne(queryWrapper);
        if (trade != null) {
            if (OrderType.ORIGIN.name().equalsIgnoreCase(type)) {
                //原始订单报文
                return trade.getResponse();
            } else if (OrderType.STANDARD.name().equalsIgnoreCase(type)) {
                //最新标准订单报文
                queryWrapper.orderByDesc("modified");
                queryWrapper.last("limit 1");
                StandardOrderData standardOrderData = tradeStandardService.getOne(queryWrapper);
                if (standardOrderData == null) {
                    return null;
                }
                return standardOrderData.getStandard();
            }
        }
        return null;
    }

    @Override
    public CommonResponse<String> createSnapshot(String tid) {
        // 查询表信息
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        Trade trade = this.getOne(queryWrapper);

        if (trade == null) {
            return new CommonResponse<String>().error(Constants.FAIL, tid + "信息不完整;");
        }

        //根据店铺获取店铺接口类型
        QueryWrapper<ShopInterfaces> shopInterfacesQueryWrapper = new QueryWrapper<>();
        shopInterfacesQueryWrapper.eq("shop", trade.getShop());
        ShopInterfaces shopInterface = shopInterfacesService.getOne(shopInterfacesQueryWrapper);
        if (shopInterface == null) {
            return new CommonResponse<String>().error(Constants.FAIL, tid + "店铺接口信息不完整;");
        }

        //标准订单快照
        StandardOrder standardOrder = null;
        if (DownloadOrderInterfaceEnum.KJY.name().equals(shopInterface.getJklx())) {
            //跨境翼接口类型
            KjyTrade kjyTrade = JSON.parseObject(trade.getResponse(), KjyTrade.class);
            standardOrder = KjyConvert.changeStandard(kjyTrade, shopInterface);
        } else if (DownloadOrderInterfaceEnum.GODS.name().equals(shopInterface.getJklx())) {
            //TODO GODS接口类型
        } else if (DownloadOrderInterfaceEnum.RDS.name().equals(shopInterface.getJklx())) {
            //淘宝RDS推送库
            RdsTradeMain rdsTrade = JSON.parseObject(trade.getResponse(), RdsTradeMain.class);
            standardOrder = RdsConvert.changeStandard(rdsTrade, shopInterface);
        }

        if (standardOrder == null) {
            return new CommonResponse<String>().error(Constants.FAIL, tid + "店铺接口类型暂不支持,生成快照失败;");
        } else {
            //获取最新的订单快照信息
            StandardOrderData latest = tradeStandardService.getLatestStandardOrderData(tid);

            if (latest == null) {
                /** 没有快照信息,则直接保存快照 */
                saveStandardOrderData(standardOrder);
            } else {
                /** 比较快照平台更新时间,若不大于最新快照,则不生成 */
                if (DateUtils.parseDate(standardOrder.getModified()).after(latest.getModified())) {
                    saveStandardOrderData(standardOrder);
                } else {
                    return new CommonResponse<String>().error(Constants.FAIL,
                            tid + "平台交易更新时间不大于已存在最新快照,不生成快照。");
                }

            }
        }

        return new CommonResponse<String>().ok(tid + "生成快照成功");
    }

    @Override
    public CommonResponse<String> updateTranformStatus(Trade trade) {

        Trade updateTrade = new Trade();
        //更新状态为已转单
        updateTrade.setFlag(TradeTranformStatusEnum.TRANFORM.getValue());

        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("tid", trade.getTid());
        updateWrapper.eq("flag", TradeTranformStatusEnum.WAIT_TRANFORM.getValue());
        boolean b = this.update(updateTrade, updateWrapper);
        if (b) {
            return new CommonResponse<String>().ok("更新成功");
        }
        return new CommonResponse<String>().error(ResponseCode.ERROR, "转单状态更新失败");
    }

    /**
     * 保存快照
     * @param standardOrder 保存订单快照信息
     */
    public void saveStandardOrderData(StandardOrder standardOrder) {

        StandardOrderData standardOrderData = new StandardOrderData();
        //平台单号
        standardOrderData.setTid(standardOrder.getTid());
        //平台更新时间
        standardOrderData.setModified(DateUtils.parseDate(standardOrder.getModified()));
        //标准数据格式快照
        String standardOrderStr = JSON.toJSONString(standardOrder);
        standardOrderData.setStandard(standardOrderStr);
        tradeStandardService.save(standardOrderData);
    }


    /**
     * 订单报文类型
     */
    private enum OrderType {

        /**
         * 原始报文
         */
        ORIGIN,
        /**
         * 标准格式报文
         */
        STANDARD;

        OrderType() {
        }
    }

}

