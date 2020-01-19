package com.lgy.system.incrementer;

import com.lgy.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description ID流水生成器
 * @Author LGy
 * @Date 2019/12/9
 */
@Service
public class IDIncrementerImpl implements IDIncrementer {

    @Autowired
    IDIncrementerForDB idIncrementerForDB;
    @Autowired
    IDIncrementerForRedis idIncrementerForRedis;

    @Value("${lgy.redis}")
    private String redis;

    @Override
    public String getNextId(String moid) {

        String nextId;

        if (Constants.ON.equals(redis)) {
            //TODO 参考美团leaf发号器
            nextId = idIncrementerForRedis.getNextId(moid);
        } else {
            nextId = idIncrementerForDB.getNextId(moid);

        }
        return nextId;
    }
}
