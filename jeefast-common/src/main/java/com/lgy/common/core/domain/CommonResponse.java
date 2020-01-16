package com.lgy.common.core.domain;

import com.alibaba.fastjson.JSON;
import com.lgy.common.constant.Constants;
import com.lgy.common.constant.ResponseCode;

/**
 * @Description 通用返回消息
 * @Author LGy
 * @Date 2019/10/14 10:20
 * <p>
 * 返回码码标识
 * @see Constants
 **/
public class CommonResponse<T> {
    private String code;
    private String msg;
    private T data;

    public CommonResponse() {
    }

    public CommonResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public CommonResponse<T> ok(T data) {
        this.code = ResponseCode.SUCCESS;
        this.data = data;
        return this;
    }


    public CommonResponse<T> success(String msg) {
        this.code = ResponseCode.SUCCESS;
        this.msg = msg;
        return this;
    }


    public CommonResponse<T> error(String code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

}
