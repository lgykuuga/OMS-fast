package com.lgy.oms.interfaces.qimen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Description 奇门接口抽象方法
 * @Author LGy
 * @Date 2019/11/21
 */
public abstract class QimenAbstractBaseSingleInterfaceImpl implements QimenSingleInterface {

	public Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> map;
	
	private  Object data;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public void setParams(Map<String, Object> map) {
		this.map = map;
	}
}
