package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StocklockMapper;
import com.lgy.oms.domain.Stocklock;
import com.lgy.oms.service.IStocklockService;

/**
 * 库存锁定 服务层实现
 *
 * @author lgy
 * @date 2019-10-18
 */
@Service
public class StocklockServiceImpl extends ServiceImpl<StocklockMapper, Stocklock> implements IStocklockService {

}