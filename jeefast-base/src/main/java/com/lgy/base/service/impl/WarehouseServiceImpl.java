package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import com.lgy.base.domain.Warehouse;
import com.lgy.base.mapper.WarehouseMapper;
import com.lgy.base.service.IWarehouseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仓库信息 服务层实现
 *
 * @author lgy
 * @date 2019-10-09
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    /**
     * redis cache value
     */
    private final String CACHE_NAMES = "warehouse";

    @Override
    public List<Warehouse> selectWarehouse() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Warehouse add(Warehouse entity) {
        boolean b = super.save(entity);
        if (b) {
            return entity;
        }
        return null;
    }

    @Override
    @CacheEvict(value = CACHE_NAMES, key = "#gco")
    public boolean delete(String gco) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gco", gco);
        return this.remove(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAMES, key = "#gco")
    public Warehouse findOne(String gco) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gco", gco);
        return getOne(queryWrapper);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Warehouse update(Warehouse entity) {
        boolean b = super.updateById(entity);
        if (b) {
            return entity;
        }
        return null;
    }
}