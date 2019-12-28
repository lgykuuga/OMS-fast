package com.lgy.oms.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.DateUtils;
import com.lgy.oms.domain.DownloadOrder;
import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.enums.order.PlatformOrderStatusEnum;
import com.lgy.oms.interfaces.common.dto.OrderDTO;
import com.lgy.oms.interfaces.rds.bean.JdpTbTrade;
import com.lgy.oms.interfaces.rds.service.IJdpTbTradeService;
import com.lgy.oms.service.IDownloadOrderService;
import com.lgy.oms.service.IShopInterfacesService;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.ITradeStandardService;
import com.lgy.oms.biz.IAsyncExecuteOrderService;
import com.lgy.oms.biz.IOrderGet;
import com.lgy.oms.biz.IRequestRemoteInterfaceService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    IDownloadOrderService downloadOrderService;

    @Autowired
    IOrderGet orderGet;

    @Autowired
    ITradeService tradeService;

    @Autowired
    ITradeStandardService tradeStandardService;

    @Autowired
    IJdpTbTradeService jdpTbTradeService;

    @Autowired
    IAsyncExecuteOrderService asyncExecuteOrderService;

    @Override
    public CommonResponse<String> getOrderListByTime(ShopInterfaces shopInterfaces, Date beginTime, Date endTime) {
        DownloadOrder downloadorder = new DownloadOrder();
        downloadorder.setBedt(beginTime);
        downloadorder.setEndt(endTime);
        //开始时间
        long startTime = System.currentTimeMillis();
        logger.debug("根据时间段下载订单:" + downloadorder.toString());
        //默认成功
        downloadorder.setStat(Constants.SUCCESS);
        //执行时间
        downloadorder.setDodt(DateUtils.getNowDate());
        //设置店铺
        downloadorder.setShop(shopInterfaces.getShop());
        //记录成功下载保存的订单数量
        downloadorder.setSnum(0);
        //记录下载并更新成功的订单数量
        downloadorder.setUnum(0);
        //记录失败下载保存的订单数量
        downloadorder.setFnum(0);
        logger.debug("开始调用订单列表接口获取订单列表");
        //开始时间
        long startOrderListDownload = System.currentTimeMillis();
        //获取订单列表
        CommonResponse<List<OrderDTO>> listResponse = orderGet.orderListDownload(shopInterfaces, beginTime, endTime);
        logger.debug("下载订单接口:(1)完成调用订单列表接口获取订单列表,耗时:[{}]ms", (System.currentTimeMillis() - startOrderListDownload));
        if (Constants.SUCCESS.equals(listResponse.getCode())) {
            List<OrderDTO> orderList = listResponse.getData();

            if (orderList != null && orderList.size() > 0) {
                //记录总订单量
                downloadorder.setOnum(orderList.size());
                logger.debug("开始根据时间段分类需要处理的订单信息");
                //开始分类处理时间
                long startGetOrderListByTime = System.currentTimeMillis();
                //定义需要下载保存的订单列表
                List<OrderDTO> saveList = new ArrayList<>();
                //定义需要下载更新的订单列表(更新未发货的订单)
                List<OrderDTO> updateList = new ArrayList<>();

                //开始订单分类处理
                classifiedOrders(orderList, saveList, updateList);
                logger.debug("下载订单接口:(2)完成根据时间段分类需要处理的订单信息,耗时:[{}]ms", (System.currentTimeMillis() - startGetOrderListByTime));
                //成功下载保存的订单数量
                int successSaveCount = 0;
                //失败下载保存的订单数量
                int faultSaveCount = 0;
                StringBuilder sbMsg = new StringBuilder();
                //需要新增保存的订单
                if (!saveList.isEmpty()) {
                    long startSaveOrderList = System.currentTimeMillis();
                    for (OrderDTO order : saveList) {
                        try {
                            //开始下载单笔订单详情时间
                            long startSave = System.currentTimeMillis();
                            //下载单笔订单详情
                            CommonResponse<Trade> tradeResponse = orderGet.orderFullInfoDownLoad(shopInterfaces, order);
                            logger.debug("完成调用订单[{}]详细信息接口(保存订单),耗时:[{}]ms", order.getTid(), (System.currentTimeMillis() - startSave));
                            if (Constants.SUCCESS.equals(tradeResponse.getCode())) {
                                Trade trade = tradeResponse.getData();
                                //保存订单快照信息
                                tradeStandardService.save(trade.getStandard());

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
                    logger.debug("下载订单接口:(3)完成保存订单列表,耗时:[{}]ms", (System.currentTimeMillis() - startSaveOrderList));
                    //记录成功下载保存的订单数量
                    downloadorder.setSnum(successSaveCount);
                    //记录失败下载保存的订单数量
                    downloadorder.setFnum(faultSaveCount);
                    downloadorder.setResp(sbMsg.toString());
                }
                //需要更新的订单
                if (!updateList.isEmpty()) {
                    //需要更新订单列表
                    List<Trade> updateTradeList = new ArrayList<>();
                    logger.debug("开始更新订单列表");

                    long startUpdate = System.currentTimeMillis();
                    for (OrderDTO order : updateList) {
                        try {
                            logger.debug("开始调用订单[{}]详情接口", order.getTid());
                            //开始获取订单
                            long startGetOrder = System.currentTimeMillis();
                            //下载单笔订单详情
                            CommonResponse<Trade> tradeResponse = orderGet.orderFullInfoDownLoad(shopInterfaces, order);
                            logger.debug("下载订单接口:完成调用订单[{}]详情接口,耗时:[{}]ms", order.getTid(), (System.currentTimeMillis() - startGetOrder));
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

                    //更新订单数量
                    int updateCount = 0;

                    for (Trade trade : updateTradeList) {

                        //保存订单快照信息
                        tradeStandardService.save(trade.getStandard());

                        //最新订单的平台最后更新时间 大于 系统订单平台最后更新时间,则进行更新
                        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("tid", trade.getTid());
                        Trade one = tradeService.getOne(queryWrapper);
                        //更新次数+1
                        trade.setFrequency(one.getFrequency() + 1);
                        //更新
                        boolean updateBoolean = tradeService.update(trade, queryWrapper);
                        if (updateBoolean) {
                            updateCount++;
                            logger.debug("订单[{}]检测到平台状态为[{}],更新订单", trade.getTid(), trade.getStatus());
                        } else {
                            logger.error("订单[{}]检测到平台状态为[{}],更新订单失败", trade.getTid(), trade.getStatus());
                            faultSaveCount++;
                            sbMsg.append(trade.getTid()).append("更新失败;");
                        }
                    }

                    //订单更新数量
                    downloadorder.setUnum(updateCount);
                    logger.debug("下载订单接口:(4)完成更新订单列表,耗时:[{}]ms", (System.currentTimeMillis() - startUpdate));
                } else {
                    logger.debug("请求成功， 没有需要更新的订单");
                }
            } else {
                downloadorder.setStat(Constants.FAIL);
                downloadorder.setOnum(0);
                downloadorder.setResp(listResponse.getMsg());
            }
        } else {
            logger.debug("请求店铺订单列表返回消息:[{}]", listResponse.getMsg());
        }
        //保存订单请求信息
        downloadOrderService.save(downloadorder);
        logger.debug("下载订单接口:下载店铺[{}]订单耗时:[{}]ms", shopInterfaces.getShop(), (System.currentTimeMillis() - startTime));
        return new CommonResponse<String>().ok("订单请求完成，请查看详细信息");
    }

    /**
     * 开始订单分类
     *
     * @param orderList  待处理订单列表
     * @param saveList   保存订单
     * @param updateList 更新订单
     */
    private void classifiedOrders(List<OrderDTO> orderList, List<OrderDTO> saveList, List<OrderDTO> updateList) {

        //定义需要更新的订单列表(发货后不再更新订单信息,不需要下载订单详情,仅更新订单状态。)
        List<OrderDTO> onlyUpdateList = new ArrayList<>();
        //定义需要取消的订单列表
        List<OrderDTO> cancelList = new ArrayList<>();
        for (OrderDTO orderDTO : orderList) {
            //判断订单是否存在
            Trade trade = tradeService.checkOrderExist(orderDTO.getTid(), orderDTO.getShop(), false);

            if (orderDTO.getPlatformState() <= PlatformOrderStatusEnum.WAIT_SELLER_SEND_GOODS.getValue()) {
                //等待卖家发货之前调用下载订单接口
                if (trade != null) {
                    if (orderDTO.getPlatformModified().after(trade.getModified())) {
                        logger.debug("订单[{}]状态为[{}],订单平台系统更新时间大于系统平台更新时间,下载订单详情",
                                orderDTO.getTid(), orderDTO.getPlatformState());
                        updateList.add(orderDTO);
                    } else {
                        logger.debug("订单[{}]状态为[{}],订单平台系统更新时间不大于系统平台更新时间,不下载订单详情",
                                orderDTO.getTid(), orderDTO.getPlatformState());
                    }
                } else {
                    logger.debug("订单[{}]状态为[{}],下载订单详情",
                            orderDTO.getTid(), orderDTO.getPlatformState());
                    saveList.add(orderDTO);
                }
            } else if (orderDTO.getPlatformState() <= PlatformOrderStatusEnum.TRADE_FINISHED.getValue()) {
                //卖家已发货到交易成功前
                if (trade != null) {
                    logger.debug("订单[{}]状态为[{}],不下载该订单详情,只更新系统存在订单的平台状态", orderDTO.getTid(), orderDTO.getPlatformState());
                    onlyUpdateList.add(orderDTO);
                } else {
                    logger.debug("订单[{}]状态为[{}],系统不存在该订单,下载该订单", orderDTO.getTid(), orderDTO.getPlatformState());
                    saveList.add(orderDTO);
                }
            } else if (orderDTO.getPlatformState() == PlatformOrderStatusEnum.TRADE_CLOSED.getValue()) {
                logger.debug("订单[{}]状态为[{}],不下载该订单并尝试取消/冻结该订单", orderDTO.getTid(), PlatformOrderStatusEnum.TRADE_CLOSED.name());
                if (trade != null) {
                    //查询存在订单,如果存在,则取消订单
                    cancelList.add(orderDTO);
                } else {
                    saveList.add(orderDTO);
                }
            } else {
                logger.debug("订单[{}]状态[{}]未定义,不下载该订单", orderDTO.getTid(), orderDTO.getPlatformState());
            }
        }

        //异步处理 更新订单、取消订单 请求
        if (!cancelList.isEmpty()) {
            logger.debug("存在更新订单,异步处理数据");
            asyncExecuteOrderService.updateOrderStatus(onlyUpdateList);
        } else if (!onlyUpdateList.isEmpty()) {
            logger.debug("存在取消订单,异步处理数据");
            asyncExecuteOrderService.cancelOrderStatus(cancelList);
        }
    }

    @Override
    public CommonResponse<String> getOrderDetailsAndSave(ShopInterfaces shopInterfaces, String tids) {
        //分割多个订单号
        String[] tidArray = tids.replaceAll(" ", "").split(",");

        List<Trade> tradeList = new ArrayList<>(tidArray.length);

        DownloadOrder downloadOrder = new DownloadOrder();
        downloadOrder.setShop(shopInterfaces.getShop());
        downloadOrder.setBedt(DateUtils.getNowDate());
        //默认成功
        downloadOrder.setStat(Constants.SUCCESS);
        //执行时间
        downloadOrder.setDodt(DateUtils.getNowDate());
        //记录总订单量
        downloadOrder.setOnum(tidArray.length);
        int sucNum = 0;
        int updNum = 0;
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
                orderDTO.setShop(shopInterfaces.getShop());
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

        if (tradeList.isEmpty()) {
            logger.debug("没有下载到订单！");
            return new CommonResponse<String>().error(Constants.FAIL, "没有下载到订单！");
        } else {
            //调用接口进行数据持久化
            for (Trade trade : tradeList) {

                Trade origin = tradeService.checkOrderExist(trade.getTid(), trade.getShop(), false);

                if (origin == null) {
                    //保存订单快照信息
                    tradeStandardService.save(trade.getStandard());

                    boolean b = tradeService.save(trade);
                    if (b) {
                        logger.debug("订单保存成功！");
                        sbMsg.append("订单保存成功！");
                        sucNum++;
                    } else {
                        logger.debug("订单[{}]保存失败", trade.getTid());
                        sbMsg.append("订单保存失败:").append(trade.getTid());
                        msg.append(trade.getTid()).append("保存失败;");
                        faiNum++;
                    }
                } else {
                    //已存在下载订单
                    if (trade.getModified().after(origin.getModified())) {
                        //最新订单的平台最后更新时间 大于 系统订单平台最后更新时间,则进行更新
                        //更新次数+1
                        trade.setFrequency(origin.getFrequency() + 1);
                        UpdateWrapper<Trade> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("tid", origin.getTid());
                        //更新
                        tradeService.update(trade, updateWrapper);
                        //保存订单快照信息
                        tradeStandardService.save(trade.getStandard());
                        updNum++;
                    } else {
                        logger.debug("订单[{}]平台更新时间不大于交易订单池平台更新时间,不更新订单", trade.getTid());
                    }
                }
            }
        }
        downloadOrder.setEndt(DateUtils.getNowDate());
        downloadOrder.setResp(msg.toString());
        //成功数量
        downloadOrder.setSnum(sucNum);
        //更新数量
        downloadOrder.setUnum(updNum);
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
    public CommonResponse<String> getOrderDetailsByTime(ShopInterfaces shopInterfaces, Date beginTime, Date endTime) {

        DownloadOrder downloadorder = new DownloadOrder();
        //开始查单时间
        downloadorder.setBedt(beginTime);
        //结束查单时间
        downloadorder.setEndt(endTime);
        //任务开始时间
        long startTime = System.currentTimeMillis();
        logger.debug("根据时间段下载订单:" + downloadorder.toString());

        //执行时间
        downloadorder.setDodt(DateUtils.getNowDate());
        //设置店铺
        downloadorder.setShop(shopInterfaces.getShop());

        //默认成功
        downloadorder.setStat(Constants.SUCCESS);
        logger.debug("开始调用订单列表接口获取订单列表");
        //开始时间
        long startOrderListDownload = System.currentTimeMillis();
        //获取订单列表
        CommonResponse<List<Trade>> listResponse = orderGet.orderFullInfoListDownload(shopInterfaces, beginTime, endTime);
        logger.debug("下载订单接口:完成调用订单列表接口获取订单列表,耗时:[{}]ms", (System.currentTimeMillis() - startOrderListDownload));
        if (Constants.SUCCESS.equals(listResponse.getCode())) {
            List<Trade> data = listResponse.getData();
            //记录总订单量
            downloadorder.setOnum(data.size());
            //成功下载保存的订单数量
            int successSaveCount = 0;
            //成功下载更新的订单数量
            int successUpdateCount = 0;
            //失败下载保存的订单数量
            int faultSaveCount = 0;
            for (Trade trade : data) {
                QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("tid", trade.getTid());
                Trade one = tradeService.getOne(queryWrapper);
                if (one == null) {
                    //订单快照
                    tradeStandardService.save(trade.getStandard());
                    boolean save = tradeService.save(trade);
                    if (save) {
                        successSaveCount++;
                    } else {
                        faultSaveCount++;
                    }
                } else {
                    if (trade.getModified().after(one.getModified())) {
                        //订单快照
                        tradeStandardService.save(trade.getStandard());
                        //更新次数+1
                        trade.setFrequency(one.getFrequency() + 1);
                        boolean update = tradeService.update(trade, queryWrapper);
                        if (update) {
                            successUpdateCount++;
                        } else {
                            faultSaveCount++;
                        }
                    } else {
                        logger.debug("订单[{}]平台更新时间[{}]不大于上次更新时间[{}],不更新订单。", trade.getTid(),
                                trade.getModified(), one.getModified());
                    }
                }
            }
            //记录成功下载保存的订单数量
            downloadorder.setSnum(successSaveCount);
            //记录下载并更新成功的订单数量
            downloadorder.setUnum(successUpdateCount);
            //记录失败下载保存的订单数量
            downloadorder.setFnum(faultSaveCount);
        } else {
            logger.debug("请求店铺订单列表返回消息:[{}]", listResponse.getMsg());
            downloadorder.setStat(Constants.FAIL);
            downloadorder.setResp(listResponse.getMsg());
        }
        //保存订单请求信息
        downloadOrderService.save(downloadorder);
        logger.debug("下载订单接口:下载店铺[{}]订单耗时:[{}]ms", shopInterfaces.getShop(), (System.currentTimeMillis() - startTime));
        return new CommonResponse<>(listResponse.getCode(), listResponse.getMsg(), null);
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

    @Override
    public CommonResponse<String> getOrderByRDS(ShopInterfaces shopInterfaces, Date bedt, Date endt) {
        DownloadOrder downloadorder = new DownloadOrder();
        downloadorder.setBedt(bedt);
        downloadorder.setEndt(endt);
        //默认成功
        downloadorder.setStat(Constants.SUCCESS);
        //执行时间
        downloadorder.setDodt(DateUtils.getNowDate());
        //设置店铺
        downloadorder.setShop(shopInterfaces.getShop());

        int successSaveCount = 0;
        int successUpdateCount = 0;
        int faultSaveCount = 0;
        //获取订单列表
        List<JdpTbTrade> jdpTbTradeList = jdpTbTradeService.getJdpTbTradeListByTime(shopInterfaces, bedt, endt);
        if (jdpTbTradeList != null && !jdpTbTradeList.isEmpty()) {
            //记录总订单量
            downloadorder.setOnum(jdpTbTradeList.size());
            for (JdpTbTrade jdpTbTrade : jdpTbTradeList) {
                QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("tid", jdpTbTrade.getTid());
                Trade one = tradeService.getOne(queryWrapper);
                if (one == null) {
                    Trade trade = jdpTbTradeService.convertTrade(jdpTbTrade, shopInterfaces);
                    //订单快照
                    tradeStandardService.save(trade.getStandard());
                    if (tradeService.save(trade)) {
                        successSaveCount++;
                    } else {
                        faultSaveCount++;
                    }
                } else {
                    if (jdpTbTrade.getModified().after(one.getModified())) {

                        Trade trade = jdpTbTradeService.convertTrade(jdpTbTrade, shopInterfaces);
                        //订单快照
                        tradeStandardService.save(trade.getStandard());
                        //更新次数+1
                        trade.setFrequency(one.getFrequency() + 1);
                        if (tradeService.update(trade, queryWrapper)) {
                            successUpdateCount++;
                        } else {
                            faultSaveCount++;
                        }
                    } else {
                        logger.debug("订单[{}]平台更新时间[{}]不大于上次更新时间[{}],不更新订单。", jdpTbTrade.getTid(),
                                jdpTbTrade.getModified(), one.getModified());
                    }
                }
            }
        } else {
            downloadorder.setStat(Constants.FAIL);
            downloadorder.setOnum(0);
            downloadorder.setResp("根据查询条件获取订单列表为空");
        }

        downloadorder.setSnum(successSaveCount);
        downloadorder.setUnum(successUpdateCount);
        downloadorder.setFnum(faultSaveCount);
        //保存订单请求信息
        downloadOrderService.save(downloadorder);
        return new CommonResponse<String>().ok("订单请求完成，请查看详细信息");
    }
}
