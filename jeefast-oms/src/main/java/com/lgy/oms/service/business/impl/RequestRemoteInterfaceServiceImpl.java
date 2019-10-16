package com.lgy.oms.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.Downloadorder;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.OrderDTO;
import com.lgy.oms.service.IDownloadorderService;
import com.lgy.oms.service.IShopInterfacesService;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.business.ICreateOrderService;
import com.lgy.oms.service.business.IOrderGet;
import com.lgy.oms.service.business.IRequestRemoteInterfaceService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 订单系统请求远程服务实现
 * @Author LGy
 * @Date 2019/10/14 15:39
 **/
@Service
public class RequestRemoteInterfaceServiceImpl implements IRequestRemoteInterfaceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IShopInterfacesService shopInterfacesService;

    @Autowired
    IDownloadorderService downloadOrderService;

    @Autowired
    IOrderGet orderGet;

    @Autowired
    ICreateOrderService createOrderService;

    @Autowired
    ITradeService tradeService;

    @Override
    public CommonResponse<String> getOrderList(String shop, Date beginTime, Date endTime) {
        Downloadorder downloadorder = new Downloadorder();
        downloadorder.setBedt(DateUtils.parseDate(beginTime));
        downloadorder.setEndt(DateUtils.parseDate(endTime));
        //开始时间
        long startTime = System.currentTimeMillis();
        logger.info("根据时间段下载订单:" + downloadorder.toString());
        //默认成功
        downloadorder.setStat(Constants.SUCCESS);
        //执行时间
        downloadorder.setDodt(DateUtils.getNowDate());
        //获取店铺接口设置
        QueryWrapper<ShopInterfaces> wrapper = new QueryWrapper<>();
        wrapper.eq("shop", shop);
        ShopInterfaces shopInterfaces = shopInterfacesService.getOne(wrapper);

        if (shopInterfaces == null || StringUtils.isEmpty(shopInterfaces.getSurl())) {
            return new CommonResponse<String>().error(Constants.FAIL, "系统设置中店铺编码不正确或没有维护店铺接口地址");
        }

        downloadorder.setShop(shop);
        //记录成功下载保存的订单数量
        downloadorder.setSnum(0);
        //记录下载并更新成功的订单数量
        downloadorder.setUnum(0);
        //记录失败下载保存的订单数量
        downloadorder.setFnum(0);
        logger.info("开始调用订单列表接口获取订单列表");
        //开始时间
        long startOrderListDownload = System.currentTimeMillis();
        //获取订单列表
        CommonResponse<List<OrderDTO>> listResponse = orderGet.orderListDownload(shopInterfaces, beginTime, endTime);
        logger.info("下载订单接口:(1)完成调用订单列表接口获取订单列表,耗时:[{}]ms", (System.currentTimeMillis() - startOrderListDownload));
        if (Constants.SUCCESS.equals(listResponse.getCode())) {
            List<OrderDTO> orderList = listResponse.getData();

            if (orderList != null && orderList.size() > 0) {
                //记录总订单量
                downloadorder.setOnum(orderList.size());
                logger.info("开始根据时间段分类需要处理的订单信息");
                //开始处理分类
                long startGetOrderListByTime = System.currentTimeMillis();
                //分类并处理订单
                Map<String, List<OrderDTO>> orderMap = createOrderService.classifiedOrders(orderList);
                logger.info("下载订单接口:(2)完成根据时间段分类需要处理的订单信息,耗时:[{}]ms", (System.currentTimeMillis() - startGetOrderListByTime));
                //成功下载保存的订单数量
                int successSaveCount = 0;
                //失败下载保存的订单数量
                int faultSaveCount = 0;
                StringBuilder sbMsg = new StringBuilder();
                //需要新增保存的订单
                if (orderMap != null && orderMap.get("save") != null && orderMap.get("save").size() > 0) {
                    logger.info("开始保存订单列表");
                    long startSaveOrderList = System.currentTimeMillis();
                    for (OrderDTO order : orderMap.get("save")) {
                        try {
                            //开始时间
                            long startSave = System.currentTimeMillis();
                            //下载单笔订单详情
                            CommonResponse<Trade> tradeResponse = orderGet.orderFullInfoDownLoad(shopInterfaces, order);
                            logger.info("完成调用订单[{}]详细信息接口(保存订单),耗时:[{}]ms", order.getTid(), (System.currentTimeMillis() - startSave));
                            if (Constants.SUCCESS.equals(tradeResponse.getCode())) {
                                Trade trade = tradeResponse.getData();

                                boolean save = tradeService.save(trade);
                                if (save) {
                                    successSaveCount++;
                                    sbMsg.append(order.getTid()).append("保存成功;");
                                } else {
                                    downloadorder.setStat(Constants.FAIL);
                                    faultSaveCount++;
                                    sbMsg.append(order.getTid()).append("保存失败;");
                                }
                            } else {
                                downloadorder.setStat(Constants.FAIL);
                                faultSaveCount++;
                                sbMsg.append(order.getTid()).append("下载失败;");
                            }
                        } catch (Exception e) {
                            logger.error("订单下载或者保存出错！", e);
                            downloadorder.setStat(Constants.FAIL);
                            faultSaveCount++;
                            sbMsg.append(order.getTid()).append("下载或保存失败;");
                        }
                    }
                    logger.info("下载订单接口:(3)完成保存订单列表,耗时:[{}]ms", (System.currentTimeMillis() - startSaveOrderList));
                    //记录成功下载保存的订单数量
                    downloadorder.setSnum(successSaveCount);
                    //记录失败下载保存的订单数量
                    downloadorder.setFnum(faultSaveCount);
                    downloadorder.setResp(sbMsg.toString());
                }
                //需要更新的订单
                if (orderMap != null && orderMap.get("update") != null && orderMap.get("update").size() > 0) {
                    //需要更新订单列表
                    List<Trade> updateTradeList = new ArrayList<>();
                    logger.info("开始更新订单列表");
                    long startUpdate = System.currentTimeMillis();
                    for (OrderDTO order : orderMap.get("update")) {
                        try {
                            logger.info("开始调用订单[{}]详情接口", order.getTid());
                            //开始获取订单
                            long startGetOrder = System.currentTimeMillis();
                            //下载单笔订单详情
                            CommonResponse<Trade> tradeResponse = orderGet.orderFullInfoDownLoad(shopInterfaces, order);
                            logger.info("下载订单接口:完成调用订单[{}]详情接口,耗时:[{}]ms", order.getTid(), (System.currentTimeMillis() - startGetOrder));
                            if (Constants.SUCCESS.equals(tradeResponse.getCode())) {
                                Trade trade = tradeResponse.getData();
                                updateTradeList.add(trade);
                            } else {
                                downloadorder.setStat(Constants.FAIL);
                                faultSaveCount++;
                                sbMsg.append(order.getTid()).append("更新下载失败;");
                            }
                        } catch (Exception e) {
                            logger.error("订单下载出错！", e);
                            downloadorder.setStat(Constants.FAIL);
                            faultSaveCount++;
                            sbMsg.append(order.getTid()).append("更新下载失败;");
                        }
                    }
                    CommonResponse<Integer> updateTradeResp = createOrderService.updateTradeInfo(updateTradeList);
                    if (Constants.SUCCESS.equals(updateTradeResp.getCode())) {
                        //订单更新数量
                        downloadorder.setUnum(updateTradeResp.getData());
                        logger.info("订单更新成功！");
                    } else {
                        logger.info("订单更新失败");
                    }
                    logger.info("下载订单接口:(4)完成更新订单列表,耗时:[{}]ms", (System.currentTimeMillis() - startUpdate));
                } else {
                    logger.info("请求成功， 没有需要更新的订单");
                }
            } else {
                downloadorder.setStat(Constants.FAIL);
                downloadorder.setOnum(0);
                downloadorder.setResp(listResponse.getMsg());
            }
        } else {
            logger.info("请求店铺订单列表返回消息:[{}]", listResponse.getMsg());
        }
        //保存订单请求信息
        downloadOrderService.save(downloadorder);
        logger.info("下载订单接口:下载店铺[{}]订单耗时:[{}]ms", shop, (System.currentTimeMillis() - startTime));
        return new CommonResponse<String>().ok("订单请求完成，请查看详细信息");
    }

    @Override
    public CommonResponse<String> getOrderDetailsAndSave(String shop, String tids, boolean getRefundDetails) {
        //获取店铺接口设置
        QueryWrapper<ShopInterfaces> wrapper = new QueryWrapper<>();
        wrapper.eq("shop", shop);
        ShopInterfaces shopInterfaces = shopInterfacesService.getOne(wrapper);

        if (shopInterfaces == null || StringUtils.isEmpty(shopInterfaces.getSurl())) {
            return new CommonResponse<String>().error(Constants.FAIL, "系统设置中店铺编码不正确或没有维护店铺接口地址");
        }
        //分割多个订单号
        String[] tidArray = tids.replaceAll(" ", "").split(",");

        List<Trade> tradeList = new ArrayList<>(tidArray.length);

        Downloadorder downloadOrder = new Downloadorder();
        downloadOrder.setShop(shop);
        downloadOrder.setBedt(DateUtils.getNowDate());
        //默认成功
        downloadOrder.setStat(Constants.SUCCESS);
        //执行时间
        downloadOrder.setDodt(DateUtils.getNowDate());
        //记录总订单量
        downloadOrder.setOnum(tidArray.length);
        int sucNum = 0;
        int faiNum = 0;
        //返回前端消息
        StringBuilder sbMsg = new StringBuilder();
        //记录订单请求记录消息
        StringBuilder msg = new StringBuilder();

        //分别根据订单编号下载订单
        for (String orderCode : tidArray) {
            try {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setTid(orderCode);
                orderDTO.setShop(shop);
                orderDTO.setOwner(shopInterfaces.getOwner());
                CommonResponse<Trade> tradeResponse = orderGet.orderFullInfoDownLoad(shopInterfaces, orderDTO);
                if (Constants.SUCCESS.equals(tradeResponse.getCode())) {
                    Trade trade = tradeResponse.getData();
                    tradeList.add(trade);
                    sbMsg.append(orderCode).append("下载成功;<br>");
                    msg.append(orderCode).append("下载成功;");
                } else {
                    sbMsg.append(orderCode).append("下载失败！").append(tradeResponse.getMsg()).append("<br>");
                    msg.append(orderCode).append("失败;");
                    downloadOrder.setStat(Constants.FAIL);
                    faiNum++;
                }
            } catch (Exception e) {
                logger.error("下载订单失败！", e);
                sbMsg.append(orderCode).append("下载失败！").append(e.getMessage()).append("<br>");
                msg.append(orderCode).append("失败;");
                downloadOrder.setStat(Constants.FAIL);
            }
        }

        if (tradeList.size() == 0) {
            logger.info("没有下载到订单！");
            return new CommonResponse<String>().error(Constants.FAIL, "没有下载到订单！");
        }else{
            //调用接口进行数据持久化
            for (Trade trade : tradeList) {
                boolean b = tradeService.save(trade);
                if (b) {
                    logger.info("订单保存成功！");
                    sbMsg.append("订单保存成功！");
                    sucNum++;
                } else {
                    logger.info("订单[{}]保存失败", trade.getTid());
                    sbMsg.append("订单保存失败:").append(trade.getTid());
                    msg.append(trade.getTid()).append("保存失败;");
                    faiNum++;
                }
            }
        }
        downloadOrder.setEndt(DateUtils.getNowDate());
        downloadOrder.setResp(msg.toString());
        //成功数量
        downloadOrder.setSnum(sucNum);
        //失败数量
        downloadOrder.setFnum(faiNum);
        //保存根据单号请求信息
        downloadOrderService.save(downloadOrder);
        if (faiNum > 0) {
            return new CommonResponse<String>().error(Constants.FAIL, sbMsg.toString());
        }
        return new CommonResponse<String>().ok(sbMsg.toString());
    }

    @Override
    public CommonResponse<String> OrderDeliveryNotice(String shop, List<T> odhxs) {
        return null;
    }

    @Override
    public CommonResponse<String> getReturnNotice(String shop, String beginTime, String endTime) {
        return null;
    }

    @Override
    public CommonResponse<String> getAppraisal(Map<String, Object> map) {
        return null;
    }
}
