package com.lgy.common.utils.xml;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @Description XML工具类
 * @Author LGy
 * @Date 2019/11/22
 */
public class XMLUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);
	
	/**
	 * 对象生成XML
	 * 
	 * @param obj
	 * @return
	 */
	public static String parseObj2XML(Object obj) {
		String xml = null;
		XStream xStream = new XStream(new DomDriver());
		xStream.autodetectAnnotations(true);
		xStream.processAnnotations(obj.getClass());
		try {
			xml = xStream.toXML(obj);
		} catch (Exception e) {
		}

		return xml;
	}

	/**
	 * 对象生成XML
	 * 
	 * @param obj
	 * @param converters
	 * @return
	 */
	public static String parseObj2XML(Object obj, List<Converter> converters) {
		String xml = null;
		XStream xStream = new XStream(new DomDriver());
		xStream.autodetectAnnotations(true);
		for (Converter converter : converters) {
			xStream.registerConverter(converter);
		}

		xStream.processAnnotations(obj.getClass());
		try {
			xml = xStream.toXML(obj);
		} catch (Exception e) {
		}

		return xml;
	}

	/**
	 * XML生成对象
	 * 
	 * @param xmlStr
	 * @param objClass
	 * @return
	 */
	public static Object parseXML2Obj(String xmlStr, Class<?> objClass) {
		Object doc = null;
		XStream xStream = new XStream(new DomDriver());
		xStream.autodetectAnnotations(true);
		xStream.processAnnotations(objClass);
		try {
			doc = xStream.fromXML(xmlStr);
		} catch (Exception e) {
			logger.error("解析XML出错",e);
		}

		return doc;
	}

	/**
	 * XML生成对象
	 * 
	 * @param xmlStr
	 * @param objClass
	 * @param converters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseXML2Obj(String xmlStr, Class<T> objClass, List<Converter> converters) {
		T doc = null;
		ETLXstreamHelper ETLXstream = new ETLXstreamHelper(new DomDriver());
		ETLXstream.autodetectAnnotations(true);
		// 注册转换器
		for (Converter converter : converters) {
			ETLXstream.registerConverter(converter);
		}
		ETLXstream.processAnnotations(objClass);
		try {
			doc = (T) ETLXstream.fromXML(xmlStr);
		} catch (Exception e) {
		}

		return doc;
	}
}
