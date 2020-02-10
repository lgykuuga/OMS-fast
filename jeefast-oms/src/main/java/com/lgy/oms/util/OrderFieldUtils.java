package com.lgy.oms.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lgy.oms.domain.order.*;
import com.lgy.system.domain.vo.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 获取订单对象字段注释中文名称
 * @Author LGy
 * @Date 2020/1/22 10:43
 **/
public class OrderFieldUtils {

    /**
     * 订单主信息
     */
    private static List<Config> orderMainConfigs;

    /**
     * 订单买家信息
     */
    private static List<Config> orderBuyerConfigs;

    /**
     * 订单支付信息
     */
    private static List<Config> orderPayConfigs;

    /**
     * 订单状态信息
     */
    private static List<Config> orderStatusConfigs;

    /**
     * 订单类型信息
     */
    private static List<Config> orderTypeConfigs;

    /**
     * 订单类型信息
     */
    private static List<Config> orderInterceptConfigs;

    /**
     * 订单类型信息
     */
    private static List<Config> orderDetailConfigs;

    /**
     * 订单所有字段信息
     */
    private static List<Config> orderFullFieldConfigs;


    public final static List<Config> getOrderMainList() {
        if (orderMainConfigs == null) {
            orderMainConfigs = new ArrayList<>(OrderMain.class.getDeclaredFields().length);
            for (Field field : OrderMain.class.getDeclaredFields()) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    orderMainConfigs.add(new Config(field.getName(), annotation.value()));
                }
            }
        }
        return orderMainConfigs;
    }

    public final static List<Config> getBuyerInfoList() {
        if (orderBuyerConfigs == null) {
            orderBuyerConfigs = new ArrayList<>(OrderBuyerInfo.class.getDeclaredFields().length);
            for (Field field : OrderBuyerInfo.class.getDeclaredFields()) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    orderBuyerConfigs.add(new Config(field.getName(), annotation.value()));
                }
            }
        }
        return orderBuyerConfigs;
    }

    public final static List<Config> getPayInfoList() {
        if (orderPayConfigs == null) {
            orderPayConfigs = new ArrayList<>(OrderPayInfo.class.getDeclaredFields().length);
            for (Field field : OrderPayInfo.class.getDeclaredFields()) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    orderPayConfigs.add(new Config(field.getName(), annotation.value()));
                }
            }
        }
        return orderPayConfigs;
    }

    public final static List<Config> getStatusInfoList() {
        if (orderStatusConfigs == null) {
            orderStatusConfigs = new ArrayList<>(OrderStatusInfo.class.getDeclaredFields().length);
            for (Field field : OrderStatusInfo.class.getDeclaredFields()) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    orderStatusConfigs.add(new Config(field.getName(), annotation.value()));
                }
            }
        }
        return orderStatusConfigs;
    }

    public final static List<Config> getTypeInfoList() {
        if (orderTypeConfigs == null) {
            orderTypeConfigs = new ArrayList<>(OrderTypeInfo.class.getDeclaredFields().length);
            for (Field field : OrderTypeInfo.class.getDeclaredFields()) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    orderTypeConfigs.add(new Config(field.getName(), annotation.value()));
                }
            }
        }
        return orderTypeConfigs;
    }

    public final static List<Config> getInterceptList() {
        if (orderInterceptConfigs == null) {
            orderInterceptConfigs = new ArrayList<>(OrderInterceptInfo.class.getDeclaredFields().length);
            for (Field field : OrderInterceptInfo.class.getDeclaredFields()) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    orderInterceptConfigs.add(new Config(field.getName(), annotation.value()));
                }
            }
        }
        return orderInterceptConfigs;
    }

    public final static List<Config> getDetailList() {
        if (orderDetailConfigs == null) {
            orderDetailConfigs = new ArrayList<>(OrderDetail.class.getDeclaredFields().length);
            for (Field field : OrderDetail.class.getDeclaredFields()) {
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    orderDetailConfigs.add(new Config(field.getName(), annotation.value()));
                }
            }
        }
        return orderDetailConfigs;
    }


    public final static List<Config> getFullFieldList() {
        if (orderFullFieldConfigs == null) {
            orderFullFieldConfigs = new ArrayList<>();
            orderFullFieldConfigs.addAll(getOrderMainList());
            orderFullFieldConfigs.addAll(getBuyerInfoList());
            orderFullFieldConfigs.addAll(getPayInfoList());
            orderFullFieldConfigs.addAll(getStatusInfoList());
            orderFullFieldConfigs.addAll(getTypeInfoList());
            orderFullFieldConfigs.addAll(getInterceptList());
            orderFullFieldConfigs.addAll(getDetailList());
        }
        return orderFullFieldConfigs;
    }




}
