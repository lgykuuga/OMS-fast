package com.lgy.oms.service.business.impl;

import com.google.gson.Gson;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.contant.ODSConstants;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.OrderDTO;
import com.lgy.oms.domain.dto.ods.request.OrderFullInfoServiceGetRequest;
import com.lgy.oms.domain.dto.ods.request.OrdersSoldServiceGetRequest;
import com.lgy.oms.domain.dto.ods.response.BaseResponse;
import com.lgy.oms.domain.dto.ods.response.order.OrderFullInfoServiceGetResponse;
import com.lgy.oms.domain.dto.ods.response.order.OrdersSoldServiceGetResponse;
import com.lgy.oms.domain.dto.ods.response.order.SaleOrderBean;
import com.lgy.oms.enums.PlatformOrderStatusEnum;
import com.lgy.oms.service.business.IODSService;
import com.lgy.oms.util.OdsHttpClient;
import com.lgy.oms.util.SignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 请求ODS接口实现
 * @Author LGy
 * @Date 2019/10/14 17:08
 **/
@Service
public class ODSServiceImpl implements IODSService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Gson gson = new Gson();

    @Override
    public BaseResponse getOrderList(Map<String, Object> map) {
        //1解析转换参数
        ShopInterfaces shopInterfaces = (ShopInterfaces) map.get("shopInterfaces");
        OrdersSoldServiceGetRequest requestBean = new OrdersSoldServiceGetRequest();
        requestBean.setBeginDate(map.get("beginDate").toString());
        requestBean.setEndDate(map.get("endDate").toString());
        requestBean.setPageNo(map.get("page").toString());
        requestBean.setPageSize(map.get("size").toString());

        //2发送请求
        String strResponse = sendRequest(shopInterfaces.getSurl(), shopInterfaces.getAppk(),
                shopInterfaces.getSecr(), ODSConstants.ORDERS_METHOD, requestBean);

        //3定义返回对象
        BaseResponse baseResponse = new BaseResponse();
        //4解析返回值
        if (StringUtils.isBlank(strResponse)) {
            baseResponse.setStatus(false);
            baseResponse.setMessage("请求没有响应或者超时！");
        } else {
            try {
                logger.info("获取订单列表返回参数:[{}]", strResponse);
                //返回消息转换为json对象
                OrdersSoldServiceGetResponse response = gson.fromJson(strResponse, OrdersSoldServiceGetResponse.class);
                //失败直接返回
                if (ODSConstants.FAILURE.equalsIgnoreCase(response.getFlag())) {
                    baseResponse.setStatus(false);
                    baseResponse.setMessage(response.getMessage());
                    return baseResponse;
                }
                List<SaleOrderBean> saleOrderBeans = response.getOrders();
                //转换为OMS内部的对象
                List<OrderDTO> orderList = new ArrayList<>();
                if (saleOrderBeans != null && saleOrderBeans.size() > 0) {
                    for (SaleOrderBean saleOrderBean : saleOrderBeans) {
                        OrderDTO order = new OrderDTO();
                        //平台交易单号
                        order.setTid(saleOrderBean.getOrderCode());
                        //平台状态
                        order.setPlatformState(PlatformOrderStatusEnum.switchStatus(saleOrderBean.getOrderStatus()));
                        //平台更新时间
                        order.setPlatformModified(DateUtils.parseDate(saleOrderBean.getUpdateTime()));
                        //货主
                        order.setOwner(shopInterfaces.getOwner());
                        //店铺
                        order.setShop(shopInterfaces.getShop());
                        orderList.add(order);
                    }
                }
                baseResponse.setBody(orderList);
                baseResponse.setStatus(true);
            } catch (Exception e) {
                baseResponse.setStatus(false);
                baseResponse.setMessage("返回消息解析失败！  " + strResponse);
            }
        }
        return baseResponse;
    }

    @Override
    public BaseResponse getOrderFullInfo(Map<String, Object> map) {
        //1解析转换参数
        ShopInterfaces shopInterfaces = (ShopInterfaces) map.get("shopInterfaces");
        OrderDTO orderDTO = (OrderDTO) map.get("orderDTO");
        OrderFullInfoServiceGetRequest requestBean = new OrderFullInfoServiceGetRequest();
        requestBean.setOrderCode(orderDTO.getTid());

        //获取退款明细
        Map<String, String> extendPropsMap = new HashMap<>(1);
        extendPropsMap.put("getRefundDetails", "true");
        requestBean.setExtendPropsMap(extendPropsMap);

        //2发送请求
        String strResponse = sendRequest(shopInterfaces.getSurl(), shopInterfaces.getAppk(),
                shopInterfaces.getSecr(), ODSConstants.ORDER_FULLINFO_METHOD, requestBean);

        //3定义返回对象
        BaseResponse baseResponse = new BaseResponse();
        //4解析返回值
        if (StringUtils.isBlank(strResponse)) {
            baseResponse.setStatus(false);
            baseResponse.setMessage("请求没有响应或者超时！");
        } else {
            try {
                //返回消息转换为json对象
                OrderFullInfoServiceGetResponse response = gson.fromJson(strResponse, OrderFullInfoServiceGetResponse.class);
                logger.info(strResponse);
                SaleOrderBean saleOrderBean = response.getOrder();
                if (saleOrderBean != null) {
                    Trade trade = new Trade();
                    //平台单号
                    trade.setTid(saleOrderBean.getOrderCode());
                    //平台交易状态
                    trade.setStatus(PlatformOrderStatusEnum.switchStatus(saleOrderBean.getOrderStatus()));
                    //平台更新时间
                    trade.setModified(DateUtils.parseDate(saleOrderBean.getUpdateTime()));
                    //设置货主
                    trade.setOwner(shopInterfaces.getOwner());
                    //设置店铺
                    trade.setShop(shopInterfaces.getShop());
                    //请求返回消息
                    trade.setResponse(strResponse);
                    baseResponse.setBody(trade);
                    baseResponse.setStatus(true);
                } else {
                    baseResponse.setStatus(false);
                    baseResponse.setMessage("没有获取到订单详细信息！  " + strResponse);
                }
            } catch (Exception e) {
                logger.error("返回消息解析失败:", e);
                baseResponse.setStatus(false);
                baseResponse.setMessage("返回消息解析失败:" + strResponse);
            }
        }
        return baseResponse;
    }

    /**
     * 发送请求
     *
     * @param url    请求ODS地址
     * @param appkey 订单接口信息接口信息
     * @param secret secret
     * @param method api方法
     * @param obj    请求对象
     * @return
     */
    private String sendRequest(String url, String appkey, String secret, String method, Object obj) {
        //内容转换为json字符串
        String body = gson.toJson(obj);
        //获取当前时间戳
        String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        //获取签名信息
        TreeMap<String, String> paramMap = new TreeMap<>();
        paramMap.put("appkey", appkey);
        paramMap.put("format", "JSON");
        paramMap.put("method", method);
        paramMap.put("timestamp", timestamp + "");
        paramMap.put("v", "1.0");
        paramMap.put("body", body);
        //获取签名
        String sign = SignatureUtil.md5Signature(paramMap, secret);
        //拼接url
        StringBuffer sbUrl = new StringBuffer();
        sbUrl.append(url).append("?");
        sbUrl.append("appkey=").append(appkey);
        sbUrl.append("&method=").append(method);
        sbUrl.append("&timestamp=").append(timestamp);
        sbUrl.append("&format=JSON");
        sbUrl.append("&sign=").append(sign);
        sbUrl.append("&v=1.0");
        logger.info("request:" + sbUrl.toString());
        logger.info("body=" + body);
        System.out.println("request:" + sbUrl.toString());
        System.out.println("body=" + body);
        //发送请求
        OdsHttpClient httpClient = new OdsHttpClient();
        return httpClient.pub(sbUrl.toString(), body);
    }
}
