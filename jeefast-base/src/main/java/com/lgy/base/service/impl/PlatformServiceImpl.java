package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import com.lgy.base.domain.Platform;
import com.lgy.base.mapper.PlatformMapper;
import com.lgy.base.service.IPlatformService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 平台档案 服务层实现
 *
 * @author lgy
 * @date 2019-10-10
 */
@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper, Platform> implements IPlatformService {

    /**
     * redis cache value
     */
    private final String CACHE_NAMES = "platform";

    @Override
    public List<Platform> selectPlatform() {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Platform> listByGcos(List<String> gcos) {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("gco", gcos);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Platform add(Platform entity) {
        boolean b = super.save(entity);
        if (b) {
            return entity;
        }
        return null;
    }

    @Override
    @CacheEvict(value = CACHE_NAMES, key = "#gco")
    public boolean delete(String gco) {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.remove(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAMES, key = "#gco")
    public Platform findOne(String gco) {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return getOne(queryWrapper);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Platform update(Platform entity) {
        boolean b = super.updateById(entity);
        if (b) {
            return entity;
        }
        return null;
    }
}