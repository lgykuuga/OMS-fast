package com.lgy.oms.interfaces.ods.bean.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * API接口请求参数
 *
 * @author admin
 */
public class OrdersSoldServiceGetRequest extends BaseExecuteParams {

    private String beginDate = null;
    private String endDate = null;
    private String pageNo = null;
    private String pageSize = null;
    private String orderStatus = null;

    //扩展参数
    private Map<String, String> extendPropsMap;

    public void analysis(String params) throws Exception {

        ObjectMapper json = new ObjectMapper();

        JsonNode root = json.readTree(params);
        if (root.get("beginDate") == null) {
            throw new Exception("content:beginDate 不能为空 !");
        }
        beginDate = root.get("beginDate").asText();

        if (root.get("endDate") == null) {
            throw new Exception("content:endDate 不能为空 !");
        }
        endDate = root.get("endDate").asText();

        if (root.get("pageNo") == null) {
            throw new Exception("content:pageNo 不能为空 !");
        }
        pageNo = root.get("pageNo").asText();

        if (root.get("pageSize") == null) {
            throw new Exception("content:pageSize 不能为空 !");
        }
        pageSize = root.get("pageSize").asText();

        //解析扩展参数
        JsonNode extendProps = root.get("extendProps");
        if (extendProps != null) {
            extendPropsMap = analysisExtendProps(extendProps);
        }
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPageNo() {
        return pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public Map<String, String> getExtendPropsMap() {
        return extendPropsMap;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public void setExtendPropsMap(Map<String, String> extendPropsMap) {
        this.extendPropsMap = extendPropsMap;
    }


}
