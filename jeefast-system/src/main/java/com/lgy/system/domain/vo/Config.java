package com.lgy.system.domain.vo;

/**
 * @Description 前端展示key/value值,用于枚举、下拉框等控件展示
 * @Author LGy
 * @Date 2019/10/23 11:43
 **/
public class Config {

    /**
     * 参数键
     */
    private String configKey;

    /**
     * 参数值
     */
    private String configValue;

    public Config(String configKey, String configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

}
