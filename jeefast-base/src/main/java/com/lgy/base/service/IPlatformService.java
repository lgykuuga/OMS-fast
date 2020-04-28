package com.lgy.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.base.domain.Platform;
import com.lgy.common.service.CommonService;

import java.util.List;

/**
 * 平台档案 服务层
 *
 * @author lgy
 * @date 2019-10-10
 */
public interface IPlatformService extends IService<Platform>, CommonService<Platform> {

    /**
     * 查询可用平台
     *
     * @return 可用平台列表
     */
    List<Platform> selectPlatform();

    /**
     * 获取平台列表
     *
     * @param gcos 平台列表编码
     * @return
     */
    List<Platform> listByGcos(List<String> gcos);
}