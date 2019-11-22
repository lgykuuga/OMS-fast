package com.lgy.common.utils.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * @Description 解析XML帮助类
 * @Author LGy
 * @Date 2019/11/22
 */
public class ETLXstreamHelper extends XStream {
	
	public ETLXstreamHelper(DomDriver domDriver){
		super(domDriver);
	}


	@Override
	protected MapperWrapper wrapMapper(MapperWrapper next) {
		return new MapperWrapper(next) {

			@Override
			public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn, String fieldName) {
				// 不能识别的节点，掠过。
				if (definedIn == Object.class) {
					return false;
				}
				// 节点名称为fileName的掠过
//				if ("fileName".equals(fieldName)) {
//					return false;
//				}
				return super.shouldSerializeMember(definedIn, fieldName);
			}
		};
	}
}
