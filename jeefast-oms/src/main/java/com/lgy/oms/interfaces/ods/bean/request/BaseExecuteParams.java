package com.lgy.oms.interfaces.ods.bean.request;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * API接口请求参数
 * @implNote
 * @author admin
 */
public class BaseExecuteParams {

    /**
     * 扩展参数解析实现公用父类
     */
    public Map<String, String> analysisExtendProps(JsonNode jsonNode) throws Exception {

        if (jsonNode == null) {
            return null;
        }

        Map<String, String> extendProps = new HashMap<String, String>();

        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String name = fieldNames.next();
            String value = jsonNode.get(name).asText();
            extendProps.put(name, value);
        }

        return null;
    }
}
