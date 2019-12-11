package com.lgy.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.base.domain.Combo;

import java.util.List;

/**
 * 组合商品 服务层
 *
 * @author lgy
 * @date 2019-10-25
 */
public interface IComboService extends IService<Combo> {

    /**
     * 根据组合商品找到组合商品明细
     *
     * @param commodity 组合商品编码
     * @return
     */
    List<Combo> getListByParent(String commodity);
}