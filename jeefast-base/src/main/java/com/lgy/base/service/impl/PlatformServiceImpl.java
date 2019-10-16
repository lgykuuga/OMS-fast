package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.base.constant.BaseConstants;
import com.lgy.common.constant.Constants;
import org.springframework.stereotype.Service;
import com.lgy.base.mapper.PlatformMapper;
import com.lgy.base.domain.Platform;
import com.lgy.base.service.IPlatformService;

import java.util.List;

/**
 * 平台档案 服务层实现
 *
 * @author lgy
 * @date 2019-10-10
 */
@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper, Platform> implements IPlatformService {

    @Override
    public List<Platform> selectPlatform() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", BaseConstants.NORMAL);
        return baseMapper.selectList(queryWrapper);
    }
}