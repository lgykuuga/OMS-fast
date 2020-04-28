package com.lgy.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.base.domain.Owner;
import com.lgy.common.service.CommonService;
import com.lgy.system.domain.vo.Config;

import java.util.List;

/**
 * 货主档案 服务层
 *
 * @author lgy
 * @date 2019-10-11
 */
public interface IOwnerService extends IService<Owner>, CommonService<Owner> {

    /**
     * 查询有效货主
     *
     * @return 货主列表
     */
    List<Config> selectOwner();

    /**
     * 根据货主编码获取货主信息
     *
     * @param gcos 货主编码集合,用
     * @return
     */
    List<Owner> listByGcos(List<String> gcos);
}