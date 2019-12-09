package com.lgy.oms.interfaces.rds.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.annotation.DataSource;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.enums.DataSourceType;
import com.lgy.common.utils.DateUtils;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.StandardOrderData;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.enums.PlatformOrderStatusEnum;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.interfaces.rds.bean.JdpTbTrade;
import com.lgy.oms.interfaces.rds.bean.RdsTradeMain;
import com.lgy.oms.interfaces.rds.mapper.JdpTbTradeMapper;
import com.lgy.oms.interfaces.rds.service.IJdpTbTradeService;
import com.lgy.oms.interfaces.rds.util.RdsConvert;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单推送库 服务层实现
 *
 * @author lgy
 * @date 2019-12-04
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class JdpTbTradeServiceImpl extends ServiceImpl<JdpTbTradeMapper, JdpTbTrade> implements IJdpTbTradeService {

    @Override
    public JdpTbTrade selectJdpTbTrade(String tid) {
        QueryWrapper<JdpTbTrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        return getOne(queryWrapper);
    }

    @Override
    public CommonResponse<Trade> createOrder2Trade(ShopInterfaces shopInterfaces, String tid) {
        JdpTbTrade jdpTbTrade = selectJdpTbTrade(tid);
        if (jdpTbTrade == null) {
            return new CommonResponse<Trade>().error(Constants.FAIL, tid + "单号不存在RDS");
        }

        Trade trade = convertTrade(jdpTbTrade, shopInterfaces);
        return new CommonResponse<Trade>().ok(trade);
    }


    @Override
    public Trade convertTrade(JdpTbTrade jdpTbTrade, ShopInterfaces shopInterfaces) {
        Trade trade = new Trade();
        //平台单号
        trade.setTid(jdpTbTrade.getTid() + "");
        //平台交易状态
        trade.setStatus(PlatformOrderStatusEnum.switchStatus(jdpTbTrade.getStatus()));
        //平台更新时间,时间戳格式
        trade.setModified(jdpTbTrade.getModified());
        //设置货主
        trade.setOwner(shopInterfaces.getOwner());
        //设置店铺
        trade.setShop(shopInterfaces.getShop());
        //请求返回消息
        String jdpResponse = jdpTbTrade.getJdpResponse();
        trade.setResponse(jdpResponse);
        /** 订单标准格式-快照 */
        StandardOrderData standardOrderData = new StandardOrderData();
        //平台单号
        standardOrderData.setTid(jdpTbTrade.getTid() + "");
        //平台更新时间
        standardOrderData.setModified(jdpTbTrade.getModified());
        ////订单标准格式
        RdsTradeMain rdsTradeMain = JSON.parseObject(jdpResponse, RdsTradeMain.class);
        StandardOrder standardOrder = RdsConvert.changeStandard(rdsTradeMain, shopInterfaces);
        standardOrderData.setStandard(JSON.toJSONString(standardOrder));
        trade.setStandard(standardOrderData);
        return trade;
    }

    @Override
    public List<JdpTbTrade> getJdpTbTradeListByTime(ShopInterfaces shopInterfaces, Date bedt, Date endt) {

        QueryWrapper queryWrapper = new QueryWrapper();
        //店铺名称
        queryWrapper.eq("seller_nick", shopInterfaces.getAppk());
        //大于等于开始时间
        queryWrapper.ge("modified", DateUtils.toStandardTime(bedt.toString()));
        //小于等于结束时间
        queryWrapper.le("modified", DateUtils.toStandardTime(endt.toString()));
        return list(queryWrapper);
    }


}