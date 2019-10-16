package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import org.springframework.stereotype.Service;
import com.lgy.base.mapper.ShopMapper;
import com.lgy.base.domain.Shop;
import com.lgy.base.service.IShopService;

import java.util.List;

/**
 * 店铺档案 服务层实现
 *
 * @author lgy
 * @date 2019-10-10
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Override
    public List<Shop> selectShop() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        return baseMapper.selectList(queryWrapper);
    }
}