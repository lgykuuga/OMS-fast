package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import com.lgy.base.domain.Owner;
import com.lgy.base.mapper.OwnerMapper;
import com.lgy.base.service.IOwnerService;
import com.lgy.system.domain.vo.Config;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 货主档案 服务层实现
 *
 * @author lgy
 * @date 2019-10-11
 */
@Service
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, Owner> implements IOwnerService {

    /**
     * redis cache value
     */
    private final String CACHE_NAMES = "owner";

    @Override
    public List<Config> selectOwner() {

        QueryWrapper<Owner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        List<Owner> list = baseMapper.selectList(queryWrapper);

        List<Config> configs = new ArrayList<>(list.size());
        for (Owner owner : list) {
            configs.add(new Config(owner.getGco(), owner.getGna()));
        }

        return configs;
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Owner add(Owner entity) {
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
    public Owner findOne(String gco) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gco", gco);
        return getOne(queryWrapper);
    }

    @Override
    @CachePut(value = CACHE_NAMES, key = "#entity.gco")
    public Owner update(Owner entity) {
        boolean b = super.updateById(entity);
        if (b) {
            return entity;
        }
        return null;
    }

}