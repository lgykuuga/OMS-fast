package com.lgy.oms.domain.dto.ods.response;

import java.io.Serializable;

/**
 * @Description 返回消息
 * @Author LGy
 * @Date 2019/10/14 17:07
 **/
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    private boolean status;

    /**
     * 内容
     */
    private Object body;

    /**
     * 消息
     */
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
