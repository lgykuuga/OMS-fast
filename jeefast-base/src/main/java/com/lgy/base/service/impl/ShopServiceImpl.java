package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import com.lgy.base.domain.Shop;
import com.lgy.base.mapper.ShopMapper;
import com.lgy.base.service.IShopService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 店铺档案 服务层实现
 *
 * @author lgy
 * @date 2019-10-10
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    /**
     * redis cache value
     */
    private final String CACHE_NAMES = "shop";

    @Override
    public List<Shop> selectShop() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        return baseMapper.selectList(queryWrapper);
    }


    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Shop add(Shop entity) {
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
    public Shop findOne(String gco) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gco", gco);
        return getOne(queryWrapper);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Shop update(Shop entity) {
        boolean b = super.updateById(entity);
        if (b) {
            return entity;
        }
        return null;
    }
}