package com.lgy.system.incrementer;

/**
 * @Description ID流水生成器
 * @Author LGy
 * @Date 2019/12/9
 */
public interface IDIncrementer {

    /**
     * 获取下一个ID
     * @param moid 模块名称
     * @return 订单号
     */
    String getNextId(String moid);
}
