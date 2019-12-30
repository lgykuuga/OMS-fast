package com.lgy.web.controller.oms.interfaces;

import com.lgy.common.utils.xml.XMLUtils;
import com.lgy.oms.interfaces.qimen.bean.QimenResponse;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.interfaces.qimen.service.QimenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * 奇门接口Controller.
 * 接收上游ERP推送给OMS的接口请求
 *
 * @author LGy
 */
@Controller
@RequestMapping("/interface/qimen")
public class QimenInterfaceController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String JSON = "JSON";

    private static final String XML = "XML";

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 奇门接口服务请求总入口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/http", method = {RequestMethod.GET, RequestMethod.POST})
    public String http(HttpServletRequest request, String app_key, String method, String timestamp,
                       String format, String sign, String sign_method, String customerId, String v) {

        String data = getData(request);
        logger.info("&data=" + data);

        HashMap<String, String> paramMap = new HashMap<>(9);
        paramMap.put("data", data);
        paramMap.put("appkey", app_key);
        paramMap.put("method", method);
        paramMap.put("timestamp", timestamp);
        paramMap.put("format", format);
        paramMap.put("sign", sign);
        paramMap.put("sign_method", sign_method);
        paramMap.put("customerId", customerId);
        paramMap.put("v", v);

        //返回消息
        QimenResponse qimenResponse = new QimenResponse();

        //参数验证
        qimenResponse = checkParam(paramMap, qimenResponse);

        //返回内容
        String rtnMessage = "";

        if (QimenConstants.SUCCESS.equals(qimenResponse.getFlag())) {
            try {
                //经过了奇门路由,会删除前缀,因此手动添加前缀匹配方法
                if (!method.contains(QimenConstants.PREFIX)) {
                    method = QimenConstants.PREFIX + method;
                }
                QimenService qimenService = (QimenService) applicationContext.getBean(method);
                if (qimenService == null) {
                    qimenResponse.setFlag(QimenConstants.SUCCESS);
                    qimenResponse.setMessage("不支持的Method：" + method);
                } else {

                    //请求执行对应的方法
                    qimenResponse = qimenService.requestExec(paramMap);
                    //奇门response响应实体转换
                    rtnMessage = responseConvert(qimenResponse, format);
                    logger.info("奇门接口服务返回：" + rtnMessage);
                    return rtnMessage;
                }
            } catch (Exception e) {
                logger.error("调用奇门接口服务异常！", e);
                qimenResponse.setFlag(QimenConstants.FAILURE);
                qimenResponse.setMessage("请求接收失败！请联系管理员");
            }
        } else {
            rtnMessage = responseConvert(qimenResponse, format);
            logger.info("奇门接口服务返回:" + rtnMessage);
        }
        return rtnMessage;
    }


    /**
     * 参数验证
     *
     * @param paramMap
     * @return
     */
    private QimenResponse checkParam(HashMap<String, String> paramMap, QimenResponse qimenResponse) {

        StringBuffer msg = new StringBuffer();

        String format = paramMap.get("format");

        if (!XML.equalsIgnoreCase(format) && !JSON.equalsIgnoreCase(format)) {
            msg.append("format格式为空或者不支持！");
        }

        if (StringUtils.isBlank(paramMap.get("appkey"))) {
            msg.append("appkey参数不能为空！");
        }

        if (StringUtils.isBlank(paramMap.get("method"))) {
            msg.append("method参数不能为空！");
        }

        if (StringUtils.isBlank(paramMap.get("sign"))) {
            msg.append("sign参数不能为空或者错误！");
        }

        if (StringUtils.isBlank(paramMap.get("customerId"))) {
            msg.append("customerId参数不能为空！");
        }

        String timestamp = paramMap.get("timestamp");

        if (StringUtils.isBlank(timestamp)) {
            msg.append("timestamp参数不能为空！");
        } else {
            try {
                timestamp = URLDecoder.decode(timestamp, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                logger.error("URLDecoder处理失败，直接按照yyyy-MM-dd HH:mm:ss格式处理");
            }
            try {
                Date date = sf.parse(timestamp);
                Long now = System.currentTimeMillis();
                //验证请求时间不能与服务器时间,相差5分钟
                if ((now + 300000L) < date.getTime() || (now - 300000L) > date.getTime()) {
                    msg.append("签名超时！");
                }
            } catch (Exception e) {
                msg.append("timestamp参数格式不正确！");
            }
        }

        if (msg.length() == 0) {
            qimenResponse.setFlag(QimenConstants.SUCCESS);
        } else {
            qimenResponse.setFlag(QimenConstants.FAILURE);
            qimenResponse.setMessage(msg.toString());
        }
        return qimenResponse;
    }


    /**
     * 读取data 数据
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getData(HttpServletRequest request) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.error("UTF-8解码失败");
        }

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }


    /**
     * 奇门response响应实体转换
     *
     * @param qimenResponse 返回消息
     * @param format        格式
     * @return
     */
    private String responseConvert(QimenResponse qimenResponse, String format) {
        if (XML.equalsIgnoreCase(format)) {
            return XMLUtils.parseObj2XML(qimenResponse);
        } else {
            return com.alibaba.fastjson.JSON.toJSONString(qimenResponse);
        }

    }

}
