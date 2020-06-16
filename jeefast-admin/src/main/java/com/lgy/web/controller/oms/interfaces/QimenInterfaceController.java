package com.lgy.web.controller.oms.interfaces;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.lgy.common.utils.xml.XMLUtils;
import com.lgy.oms.interfaces.qimen.bean.QimenParam;
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
import java.util.Date;


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

    private static final String ENCODE = "UTF-8";

    private static final Long TIME_DIFFERENT = 300000L;

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

        QimenParam qimenParam = new QimenParam();
        qimenParam.setData(data);
        qimenParam.setAppkey(app_key);
        qimenParam.setMethod(method);
        qimenParam.setTimestamp(timestamp);
        qimenParam.setFormat(format);
        qimenParam.setSign(sign);
        qimenParam.setSign_method(sign_method);
        qimenParam.setCustomerId(customerId);
        qimenParam.setV(v);

        //返回消息
        QimenResponse qimenResponse = new QimenResponse();

        //参数验证
        checkParam(qimenParam, qimenResponse);

        //返回内容
        String rtnMessage = StringUtils.EMPTY;

        if (QimenConstants.SUCCESS.equals(qimenResponse.getFlag())) {
            try {
                //经过了奇门路由,会删除前缀,因此手动添加前缀匹配方法
                if (!method.contains(QimenConstants.PREFIX)) {
                    method = QimenConstants.PREFIX + method;
                }
                QimenService qimenService = (QimenService) applicationContext.getBean(method);
                if (qimenService == null) {
                    qimenResponse.setFlag(QimenConstants.SUCCESS);
                    qimenResponse.setMessage("不支持的Method:" + method);
                } else {

                    //请求执行对应的方法
                    qimenResponse = qimenService.requestExec(qimenParam);
                    //奇门response响应实体转换
                    rtnMessage = responseConvert(qimenResponse, format);
                    logger.info("奇门接口服务返回:" + rtnMessage);
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
     * @param qimenParam    奇门接口请求参数
     * @param qimenResponse 返回值
     */
    private void checkParam(QimenParam qimenParam, QimenResponse qimenResponse) {

        StringBuilder msg = new StringBuilder();

        if (!StringUtils.equalsAnyIgnoreCase(qimenParam.getFormat(), QimenConstants.XML, QimenConstants.JSON)) {
            msg.append("format格式为空或者不支持！");
        }

        if (StringUtils.isBlank(qimenParam.getAppkey())) {
            msg.append("appkey参数不能为空！");
        }

        if (StringUtils.isBlank(qimenParam.getMethod())) {
            msg.append("method参数不能为空！");
        }

        if (StringUtils.isBlank(qimenParam.getSign())) {
            msg.append("sign参数不能为空或者错误！");
        }

        if (StringUtils.isBlank(qimenParam.getCustomerId())) {
            msg.append("customerId参数不能为空！");
        }

        String timestamp = qimenParam.getTimestamp();

        if (StringUtils.isBlank(timestamp)) {
            msg.append("timestamp参数不能为空！");
        } else {
            try {
                timestamp = URLDecoder.decode(timestamp, ENCODE);
            } catch (UnsupportedEncodingException e1) {
                logger.error("URLDecoder处理失败，直接按照yyyy-MM-dd HH:mm:ss格式处理");
            }
            try {
                Date date = DateUtil.parseDateTime(timestamp);
                Long now = System.currentTimeMillis();
                //验证请求时间不能与服务器时间,相差5分钟
                if ((now + TIME_DIFFERENT) < date.getTime() || (now - TIME_DIFFERENT) > date.getTime()) {
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
    }


    /**
     * 读取data 数据
     *
     * @param request HttpServletRequest
     * @return data
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
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), ENCODE));
            String line;
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
     * @return 格式体
     */
    private String responseConvert(QimenResponse qimenResponse, String format) {
        if (QimenConstants.XML.equalsIgnoreCase(format)) {
            return XMLUtils.parseObj2XML(qimenResponse);
        } else {
            return JSON.toJSONString(qimenResponse);
        }

    }

}
