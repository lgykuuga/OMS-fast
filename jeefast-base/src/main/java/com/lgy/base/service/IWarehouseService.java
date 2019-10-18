package com.lgy.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.base.domain.Warehouse;

import java.util.List;

/**
 * 仓库信息 服务层
 *
 * @author lgy
 * @date 2019-10-09
 */
public interface IWarehouseService extends IService<Warehouse> {

    /**
     * 下拉框查询仓库
     * @return
     */
    List<Warehouse> selectWarehouse();
}