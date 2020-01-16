package com.lgy.common.constant;

/**
 * 返回消息编码值
 *
 * @author lgy
 */
public class ResponseCode {

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用业务失败标识,人工处理
     */
    public static final String FAIL = "1";

    /**
     * 通用系统记录代码异常,需要开发人员关注
     */
    public static final String ERROR = "2";

    /**
     * 通用系统失败异常,需要记录,比如需要MQ重发。
     */
    public static final String RESEND = "3";

}
