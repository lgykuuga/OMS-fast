package com.lgy.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.base.domain.Commodity;
import com.lgy.common.service.CommonService;

import java.util.List;

/**
 * 商品档案 服务层
 *
 * @author lgy
 * @date 2019-10-09
 */
public interface ICommodityService extends IService<Commodity>, CommonService<Commodity> {

    /**
     * 导入数据
     *
     * @param commoditys    商品档案数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName      操作用户
     * @return 结果
     */
    String importData(List<Commodity> commoditys, boolean updateSupport, String operName);

    /**
     * 根据商品编码查询商品档案
     *
     * @param gco 商品编码
     * @return
     */
    Commodity getOne(String gco);

}