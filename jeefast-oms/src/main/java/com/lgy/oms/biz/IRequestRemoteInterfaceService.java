package com.lgy.oms.biz;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.ShopInterfaces;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 订单系统请求远程服务
 * @Author LGy
 * @Date 2019/10/14
 */
public interface IRequestRemoteInterfaceService {

    /**
     * 根据时间获取订单
     *
     * @param shopInterfaces 店铺接口设置
     * @param beginTime      开始时间
     * @param endTime        结束时间
     * @return 返回结果
     */
    CommonResponse<String> getOrderListByTime(ShopInterfaces shopInterfaces, Date beginTime, Date endTime);

    /**
     * 获取订单详细信息并保存
     *
     * @param shopInterfaces 店铺接口设置
     * @param tids           多个订单号用','分隔
     */
    CommonResponse<String> getOrderDetailsAndSave(ShopInterfaces shopInterfaces, String tids);

    /**
     * 根据时间获取订单详细信息并保存
     *
     * @param shopInterfaces 店铺接口设置
     * @param beginTime      开始时间
     * @param endTime        结束时间
     */
    CommonResponse<String> getOrderDetailsByTime(ShopInterfaces shopInterfaces, Date beginTime, Date endTime);

    /**
     * 订单发货通知
     *
     * @param shop  店铺编码
     * @param odhxs 订单回传信息
     * @return
     */
    CommonResponse<String> OrderDeliveryNotice(String shop, List<T> odhxs);

    /**
     * 获取退货退款通知
     *
     * @param shop      店铺编码
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    CommonResponse<String> getReturnNotice(String shop, String beginTime, String endTime);

    /**
     * 获取评价接口
     *
     * @param map 请求参数
     * @return
     */
    CommonResponse<String> getAppraisal(Map<String, Object> map);

    /**
     * 根据时间段在RDS获取订单详细信息并保存
     *
     * @param shopInterfaces 店铺接口设置
     * @param bedt           开始时间
     * @param endt           结束时间
     * @return
     */
    CommonResponse<String> getOrderByRDS(ShopInterfaces shopInterfaces, Date bedt, Date endt);
}
