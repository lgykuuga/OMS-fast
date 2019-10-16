package com.lgy.base.service;

import com.lgy.base.domain.Platform;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 平台档案 服务层
 *
 * @author lgy
 * @date 2019-10-10
 */
public interface IPlatformService extends IService<Platform> {

    /**
     * 查询可用平台
     * @return 可用平台列表
     */
    List<Platform> selectPlatform();
}