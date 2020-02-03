package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import com.lgy.base.domain.Logistics;
import com.lgy.base.mapper.LogisticsMapper;
import com.lgy.base.service.ILogisticsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物流商信息 服务层实现
 *
 * @author lgy
 * @date 2019-10-09
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements ILogisticsService {

    /**
     * redis cache value
     */
    private final String CACHE_NAMES = "logistics";

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Logistics add(Logistics entity) {
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
    public Logistics findOne(String gco) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gco", gco);
        return getOne(queryWrapper);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Logistics update(Logistics entity) {
        boolean b = super.updateById(entity);
        if (b) {
            return entity;
        }
        return null;
    }

    @Override
    public List<Logistics> selectLogistics() {
        QueryWrapper<Logistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        return baseMapper.selectList(queryWrapper);
    }
}