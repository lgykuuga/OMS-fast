package com.lgy.oms.interfaces.ods.bean.response;

/**
 * API接口响应实体
 * @author admin
 *
 */
public class BaseExecuteResponse {

	/**
	 * 响应信息
	 */
	private String message;
	/**
	 * success|failure
	 */
	private String flag;
	/**
	 * 响应码
	 */
	private String code;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
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

}
