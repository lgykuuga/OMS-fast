package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.MqErrorMessage;
import com.lgy.oms.mapper.MqErrorMessageMapper;
import com.lgy.oms.service.IMqErrorMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * MQ数据异常记录 服务层实现
 *
 * @author lgy
 * @date 2019-12-26
 */
@Service
public class MqErrorMessageServiceImpl extends ServiceImpl<MqErrorMessageMapper, MqErrorMessage>
        implements IMqErrorMessageService {

    private static Logger logger = LoggerFactory.getLogger(MqErrorMessageServiceImpl.class);

}