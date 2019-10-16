package com.lgy.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.lgy.base.mapper.LogisticsMapper;
import com.lgy.base.domain.Logistics;
import com.lgy.base.service.ILogisticsService;

/**
 * 物流商信息 服务层实现
 *
 * @author lgy
 * @date 2019-10-09
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements ILogisticsService {

}