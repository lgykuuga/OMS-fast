package com.lgy.oms.interfaces.qimen.service;

import com.lgy.oms.interfaces.qimen.bean.QimenParam;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;


/**
 * 奇门接口服务,奇门接口被动接收请求业务执行
 *
 * @author LGy
 */
public interface QimenService {

    /**
     * 请求执行方法
     *
     * @param qimenParam 参数
     * @return
     */
    QimenResponse requestExec(QimenParam qimenParam);

}
