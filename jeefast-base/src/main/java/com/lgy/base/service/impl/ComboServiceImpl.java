package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import org.springframework.stereotype.Service;
import com.lgy.base.mapper.ComboMapper;
import com.lgy.base.domain.Combo;
import com.lgy.base.service.IComboService;

import java.util.List;

/**
 * 组合商品 服务层实现
 *
 * @author lgy
 * @date 2019-10-25
 */
@Service
public class ComboServiceImpl extends ServiceImpl<ComboMapper, Combo> implements IComboService {

    @Override
    public List<Combo> getListByParent(String commodity) {
        QueryWrapper<Combo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent", commodity);
        queryWrapper.eq("status", Constants.ENABLE);
        return list(queryWrapper);
    }
}