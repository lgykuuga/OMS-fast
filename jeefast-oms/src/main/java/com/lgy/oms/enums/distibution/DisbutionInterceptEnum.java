package com.lgy.oms.enums.distibution;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 配货单拦截状态
 * @author Haru Skch
 * @since 2019-12-21
 */
public enum DisbutionInterceptEnum {

    /**
     * 无拦截
     */
    NONE(0, "无拦截"),

    /**
     * 分配仓库拦截
     */
    WAREHOUSE(1, "分配仓库拦截"),

    /**
     * 分配物流商拦截
     */
    LOGISTICS(2, "分配物流商拦截"),

    /**
     * 取消请求拦截
     */
    CANCLE(3, "取消请求拦截"),

    /**
     * 客服标记拦截
     */
    CUSTOMER(4, "客服标记拦截"),

    /**
     * 委托发货拦截
     */
    SENDOUT(5, "委托发货拦截"),

    /**
     * 系统异常(try-catch)
     */
    OTHERS(99, "其它异常");

    private Integer value;
    private String name;

    private static List<Config> list = new ArrayList<>();

    DisbutionInterceptEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Config> getList(){
        if(list.isEmpty()){
            for (DisbutionInterceptEnum temp : DisbutionInterceptEnum.values()) {
                list.add(new Config(temp.value.toString(), temp.getName()));
            }
        }
        return list;
    }
}
