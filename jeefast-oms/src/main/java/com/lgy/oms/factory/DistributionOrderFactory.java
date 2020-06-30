package com.lgy.oms.factory;

import com.lgy.oms.domain.distribution.DistributionDetail;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description DistributionOrderFactory
 * @Author LGy
 * @Date 2020/6/18 10:28
 **/
public class DistributionOrderFactory {

    /**
     * 转换,生成配货单
     *
     * @param orderMain 主订单信息
     * @param param     配货参数
     * @return 配货单
     */
    public static DistributionOrder convert(OrderMain orderMain, DistributionParamDTO param) {
        //配货单信息
        DistributionOrder distributionOrder = new DistributionOrder();
        BeanUtils.copyProperties(orderMain, distributionOrder);
        //生成配货单号
        String distributionId = createIdRule(orderMain);

        distributionOrder.setDistributionId(distributionId);

        List<OrderDetail> orderDetails = orderMain.getOrderDetails();

        List<DistributionDetail> distributionDetailList = new ArrayList<>(orderDetails.size());

        for (OrderDetail orderDetail : orderDetails) {
            DistributionDetail distributionDetail = new DistributionDetail();
            BeanUtils.copyProperties(orderDetail, distributionDetail);
            distributionDetail.setDistributionId(distributionId);
            distributionDetailList.add(distributionDetail);
        }

        distributionOrder.setDistributionDetail(distributionDetailList);
        return distributionOrder;
    }

    /**
     * 创建流水号ID规则
     *
     * @param orderMain 订单主信息
     * @return 配货单ID流水号
     */
    private static String createIdRule(OrderMain orderMain) {

        /**
         * 配货单id = 订单Id - 生成配货单数量
         *例:
         *  OD20200131拆分成2个配货单
         *  OD20200131-0
         *  OD20200131-1
         *
         *  ID唯一性靠业务保证,无须使用redis缓存校验.
         */
        return orderMain.getOrderId()
                + "-"
                + orderMain.getDistributionOrderQty();
    }

}
