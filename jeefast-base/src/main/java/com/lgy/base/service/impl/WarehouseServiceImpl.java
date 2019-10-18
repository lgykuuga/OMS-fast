package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import org.springframework.stereotype.Service;
import com.lgy.base.mapper.WarehouseMapper;
import com.lgy.base.domain.Warehouse;
import com.lgy.base.service.IWarehouseService;

import java.util.List;

/**
 * 仓库信息 服务层实现
 *
 * @author lgy
 * @date 2019-10-09
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    @Override
    public List<Warehouse> selectWarehouse() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        return baseMapper.selectList(queryWrapper);
    }
}