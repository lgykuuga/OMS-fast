package com.lgy.oms.interfaces.qimen.bean;

/**
 * @Description 奇门通用Response
 * @Author LGy
 * @Date 2019/11/20 15:14
 **/
public class QimenResponse {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    /**
     * 响应结果:success|failure
     */
    private String flag;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    public QimenResponse() {
    }

    public QimenResponse success(String message) {
        this.flag = SUCCESS;
        this.message = message;
        return this;
    }

    public QimenResponse success(String code, String message) {
        this.flag = FAILURE;
        this.code = code;
        this.message = message;
        return this;
    }

    public QimenResponse(String flag, String code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
