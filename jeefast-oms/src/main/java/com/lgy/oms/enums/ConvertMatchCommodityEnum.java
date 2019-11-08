package com.lgy.oms.enums;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 转单匹配商品方式
 * @Author LGy
 * @Date 2019/11/1 17:01
 **/
public enum ConvertMatchCommodityEnum {

    /**
     * 订单外部编码
     */
    ORDER_OUT_NUM("0", "订单外部编码"),
    /**
     * 铺货关系
     */
    SHOP_COMMODITY("1", "铺货关系");

    private final String key;
    private final String value;

    /**
     * 节点集合
     */
    private static List<Config> configs;

    /**
     * 获取节点集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>(ConvertMatchCommodityEnum.values().length);
            for (ConvertMatchCommodityEnum matchCommodityEnum : ConvertMatchCommodityEnum.values()) {
                configs.add(new Config(matchCommodityEnum.getKey(), matchCommodityEnum.getValue()));
            }
        }
        return configs;
    }

    ConvertMatchCommodityEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
