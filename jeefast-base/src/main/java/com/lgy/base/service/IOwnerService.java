package com.lgy.base.service;

import com.lgy.base.domain.Owner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.system.domain.vo.Config;

import java.util.List;

/**
 * 货主档案 服务层
 *
 * @author lgy
 * @date 2019-10-11
 */
public interface IOwnerService extends IService<Owner> {

    /**
     * 查询有效货主
     * @return 货主列表
     */
    List<Config> selectOwner();
}