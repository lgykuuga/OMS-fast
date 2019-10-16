package com.lgy.oms.domain.dto.ods.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * API接口请求参数
 *
 * @author wonderful
 */
public class OrderFullInfoServiceGetRequest extends BaseExecuteParams {

    private String orderCode = null;

    //扩展参数
    private Map<String, String> extendPropsMap;

    public void analysis(String params) throws Exception {

        ObjectMapper json = new ObjectMapper();

        JsonNode root = json.readTree(params);
        if (root.get("orderId") == null) {
            throw new Exception("content:orderCode 不能为空 !");
        }

        orderCode = root.get("orderCode").asText();

        //解析扩展参数
        JsonNode extendProps = root.get("extendProps");
        if (extendProps != null) {
            extendPropsMap = analysisExtendProps(extendProps);
        }
    }

    public Map<String, String> getExtendPropsMap() {
        return extendPropsMap;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setExtendPropsMap(Map<String, String> extendPropsMap) {
        this.extendPropsMap = extendPropsMap;
    }


}
