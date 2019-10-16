package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.lgy.base.mapper.WarehouseMapper;
import com.lgy.base.domain.Warehouse;
import com.lgy.base.service.IWarehouseService;

/**
 * 仓库信息 服务层实现
 *
 * @author lgy
 * @date 2019-10-09
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

}