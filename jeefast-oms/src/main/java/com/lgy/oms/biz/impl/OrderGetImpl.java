package com.lgy.oms.biz.impl;


import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.DateUtils;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.enums.strategy.DownloadOrderInterfaceEnum;
import com.lgy.oms.interfaces.common.dto.OrderDTO;
import com.lgy.oms.interfaces.kjy.service.IKJYService;
import com.lgy.oms.interfaces.ods.bean.response.BaseResponse;
import com.lgy.oms.interfaces.ods.service.IODSService;
import com.lgy.oms.interfaces.rds.service.IJdpTbTradeService;
import com.lgy.oms.biz.IOrderGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description 订单获取接口实现
 * @Author LGy
 * @Date 2019/10/14
 */
@Service
public class OrderGetImpl implements IOrderGet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IODSService odsService;

    @Autowired
    IKJYService kjyService;

    /** RDS Service */
    @Autowired
    IJdpTbTradeService jdpTbTradeService;

    @Override
    public CommonResponse<List<OrderDTO>> orderListDownload(ShopInterfaces shopInterfaces, Date beginDate, Date endDate) {
        logger.debug("调用获取店铺[{}]订单列表服务：beginDate[{}],endDate[{}]", shopInterfaces.getShop(), beginDate, endDate);
        //默认返回成功内容
        CommonResponse<List<OrderDTO>> message = new CommonResponse<>();
        message.setCode(Constants.SUCCESS);

        List<OrderDTO> orderBeanList = new ArrayList<>();

        String startTime = DateUtils.toStandardTime(beginDate.toString());
        String endTime = DateUtils.toStandardTime(endDate.toString());

        int size = 50;
        int page = 1;
        int count = 0;
        int returnListSize = 0;

        //订单号集合
        Set<String> orderCodeSet = new HashSet<>();

        //1定义转换参数map
        Map<String, Object> parametersMap = new HashMap<>(5);
        parametersMap.put("shopInterfaces", shopInterfaces);
        parametersMap.put("beginDate", startTime);
        parametersMap.put("endDate", endTime);

        do {
            parametersMap.put("size", size);
            parametersMap.put("page", page);

            try {
                //本次获取订单数量
                int thisCount = 0;
                //调用接口
                BaseResponse response = odsService.getOrderList(parametersMap);
                if (response.isStatus()) {
                    List<OrderDTO> returnList = (List<OrderDTO>) response.getBody();
                    for (OrderDTO orderBean : returnList) {
                        //确保分页订单不重复
                        if (orderCodeSet.add(orderBean.getTid())) {
                            orderBeanList.add(orderBean);
                            thisCount++;
                        }
                    }
                    count += thisCount;
                    returnListSize = returnList.size();
                } else {
                    logger.error("分页第" + page + "次请求失败：" + response.getMessage());
                    message.setMsg("分页第" + page + "次调用下单接口出错：" + response.getMessage());
                }
            } catch (Exception e) {
                logger.error("调用下单接口出错：", e);
                message.setCode(Constants.FAIL);
                message.setMsg("调用下单接口出错：" + e.getMessage());
            }

            page += 1;
        } while (count >= size * (page - 1) && returnListSize != 0 && page < 100);

        message.setData(orderBeanList);
        return message;
    }

    @Override
    public CommonResponse<Trade> orderFullInfoDownLoad(ShopInterfaces shopInterfaces, OrderDTO orderDTO) {

        if (DownloadOrderInterfaceEnum.GODS.name().equals(shopInterfaces.getJklx())) {
            //ODS 下载订单明细
            return odsDownloadOrderFullInfo(shopInterfaces, orderDTO);
        }

        if (DownloadOrderInterfaceEnum.RDS.name().equals(shopInterfaces.getJklx())) {
            //RDS推送库获取订单明细,一般不会用订单号去手动获取订单,所以偷懒不改代码,一条条获取.
            return jdpTbTradeService.createOrder2Trade(shopInterfaces, orderDTO.getTid());
        }

        return new CommonResponse<Trade>().error(Constants.FAIL, "接口未定义");

    }

    /**
     * ODS 下载订单明细
     * @param shopInterfaces 订单接口
     * @param orderDTO 订单信息
     * @return
     */
    private CommonResponse<Trade> odsDownloadOrderFullInfo(ShopInterfaces shopInterfaces, OrderDTO orderDTO) {
        logger.debug("调用下载订单服务：订单编号[{}]", orderDTO.getTid());
        //1自定义转换参数map
        Map<String, Object> parametersMap = new HashMap<>(3);
        parametersMap.put("shopInterfaces", shopInterfaces);
        parametersMap.put("orderCode", orderDTO.getTid());
        parametersMap.put("orderDTO", orderDTO);
        //3调用接口
        BaseResponse response = odsService.getOrderFullInfo(parametersMap);

        //4返回
        if (response.isStatus()) {
            return new CommonResponse<Trade>().ok((Trade) response.getBody());
        } else {
            logger.error(response.getMessage());
            return new CommonResponse<Trade>().error(Constants.FAIL, response.getMessage());
        }
    }

    @Override
    public CommonResponse<List<Trade>> orderFullInfoListDownload(ShopInterfaces shopInterfaces, Date beginDate, Date endDate) {
        logger.info("调用获取店铺[{}]订单列表服务：beginDate[{}],endDate[{}]", shopInterfaces.getShop(), beginDate, endDate);
        String startTime = DateUtils.toStandardTime(beginDate.toString());
        String endTime = DateUtils.toStandardTime(endDate.toString());
        return kjyService.getOrderFullInfoList(shopInterfaces, startTime, endTime);

    }


}
