package com.lgy.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.base.domain.Logistics;
import com.lgy.common.service.CommonService;

import java.util.List;

/**
 * 物流商信息 服务层
 *
 * @author lgy
 * @date 2019-10-09
 */
public interface ILogisticsService extends IService<Logistics>, CommonService<Logistics> {

    /**
     * 下拉框查询物流商
     * @return
     */
    List<Logistics> selectLogistics();
}