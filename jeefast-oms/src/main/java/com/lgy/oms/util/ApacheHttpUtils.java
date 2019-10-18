package com.lgy.oms.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * HttpUtils 请求客户端
 *
 * @author ck
 * @date 2019/9/9
 */
public class ApacheHttpUtils {

    private static Logger logger = LoggerFactory.getLogger(ApacheHttpUtils.class);

    public static String doPost(String url, Map<String, Object> params) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        CloseableHttpResponse response = null;
        String rest = null;

        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setConnectionRequestTimeout(60 * 1000)
                .build();

        try {
            httpPost.setHeader("token", "35D82EBFA2588BF4C2977BD6F7CF9DC7");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            httpPost.setConfig(requestConfig);

            //组装请求参数
            List<NameValuePair> nvps = new ArrayList<>();
            for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext(); ) {
                String key = (String) iter.next();
                String value = String.valueOf(params.get(key));
                nvps.add(new BasicNameValuePair(key, value));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

            //执行
            response = httpClient.execute(httpPost);
            //返回结果集
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                rest = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            }
        } catch (IOException e) {
            logger.error("请求失败:", e);
        } finally {
            //关闭流
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.error("关闭流失败:", e);
            }
        }


        return rest;

    }

    public static String doGet(String url) {

        String rest = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                rest = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            }

        } catch (IOException e) {
            System.out.printf("e" + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                System.out.printf("e" + e);
            }
        }


        return null;

    }
}
