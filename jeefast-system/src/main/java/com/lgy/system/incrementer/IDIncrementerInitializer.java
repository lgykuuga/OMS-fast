package com.lgy.system.incrementer;

import com.lgy.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author LGy
 * @Date 2019/12/9
 */
@Component(value = "idIncrementerInitializer")
public class IDIncrementerInitializer {


    @Value("{lgy.redis}")
    private String redis;

    /**
     * 每个模块一个内部对象
     */
    public static Map<String, IDIncrementerBean> IDMODE =
            Collections.synchronizedMap(new HashMap<>(128));

    @Autowired
    private IDIncrementerForDB idIncrementerForDB;

    /**
     * 初始化方法
     * synchronized 和 if 双重验证，防并发重复初始化
     */
    public synchronized void initializer() {

        // 双重验证，防并发重复初始化
        if (!IDMODE.isEmpty()) {
            return;
        }
        // 初始化mode
        initIDMode();

        initIDbyMysql();
    }

    /**
     * 重新全量初始化
     *
     * @param cid 客户ID
     */
    public synchronized void reload(String cid) {

        // 初始化mode
        IDMODE.clear();
        initIDMode();

        initIDbyMysql();
    }

    /**
     * 基于mysql生成ID
     */
    private void initIDMode() {
        idIncrementerForDB.initIDMode(IDMODE);
    }

    /**
     * 基于mysql生成ID
     */
    public void initIDbyMysql() {
        idIncrementerForDB.initIDbyMysql();
    }

}
