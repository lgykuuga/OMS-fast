package com.lgy.oms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description ODS请求Http客户端
 * @Author LGy
 * @Date 2019/10/14
 */
public class OdsHttpClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String method = null;

    public String getMethod() {
        if (method == null) {
            return "POST";
        }
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @param serviceURL webService地址.
     * @param param
     * @return
     */
    public String pub(String serviceURL, String param) {
        URL url = null;
        HttpURLConnection connection = null;
        StringBuffer buffer = new StringBuffer();
        try {
            url = new URL(serviceURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(getMethod());
            connection.setRequestProperty("Content-Length", param.length() + "");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(90000);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(param.getBytes("UTF-8"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
        } catch (IOException e) {
            logger.debug(null, e);
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }

        logger.debug("response:" + buffer.toString());
        return buffer.toString();
    }
}
