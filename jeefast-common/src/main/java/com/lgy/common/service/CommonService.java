package com.lgy.common.service;

/**
 * @Description CommonService,实现CRUD功能,
 * 目的是为了基础资料实现redis缓存机制。
 * @Author LGy
 * @Date 2020/1/17
 */
public interface CommonService<T> {

    /**
     * 新增
     * @param t
     * @return
     */
    T add(T t);

    /**
     * 删除
     * @param gco
     * @return gco
     */
    boolean delete(String gco);

    /**
     * 查找唯一
     * @param key
     * @return
     */
    T findOne(String key);

    /**
     * 更新
     * @param t
     * @return
     */
    T update(T t);
}
