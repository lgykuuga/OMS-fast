package com.lgy.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.system.domain.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(MetaHandler.class);

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        SysUser userEntity = ShiroUtils.getSysUser();
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", userEntity.getLoginName(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userEntity.getLoginName(), metaObject);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        SysUser userEntity = ShiroUtils.getSysUser();
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userEntity.getLoginName(), metaObject);
    }

}
