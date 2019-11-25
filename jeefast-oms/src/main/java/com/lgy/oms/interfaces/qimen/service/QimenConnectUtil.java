package com.lgy.oms.interfaces.qimen.service;

import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.security.Md5Utils;
import com.lgy.oms.domain.WarehouseInterfaces;
import com.lgy.oms.interfaces.qimen.contant.QimenConstants;
import com.lgy.oms.domain.ShopInterfaces;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * 调用奇门公用方法
 *
 * @author LGy
 */
public class QimenConnectUtil {

    public Logger logger = LoggerFactory.getLogger(getClass());

    public static final int SUCCESS_CODE = 200;

    private String method;
    private String timestamp = DateUtils.getTime();
    private String v = "2.0";
    private String sign_method = "md5";
    private String format = "xml";

    /**
     * 调用奇门接口方法
     *
     * @param method 请求到奇门的方法
     * @param type   类型：wms和erp
     * @param object 接口对象
     * @param body   请求对象
     * @return
     * @注 appkey 对应 奇门的 customerId
     */
    public String connet(String method, String type, Object object, String body) throws Exception {

        String url = "";
        String appkey = "";
        String cuid = "";
        String secret = "";
        if (QimenConstants.WMS.equals(type)) {
            WarehouseInterfaces warehouseInterfaces = (WarehouseInterfaces) object;
            url = warehouseInterfaces.getSurl();
            appkey = warehouseInterfaces.getAppk();
            cuid = warehouseInterfaces.getToke();
            secret = warehouseInterfaces.getSecr();
            //格式
            format = warehouseInterfaces.getFmat();
            //跳过奇门路由,直接请求到WMS。删除请求方法名前缀(taobao.qimen.)
            if (!QimenConstants.URL.equals(url)) {
                method = method.replace(QimenConstants.PREFIX, "");
            }
        } else if (QimenConstants.ERP.equals(type)) {
            ShopInterfaces shopInterfaces = (ShopInterfaces) object;
            url = shopInterfaces.getSurl();
            appkey = shopInterfaces.getAppk();
            cuid = shopInterfaces.getToke();
            secret = shopInterfaces.getSecr();
        }

        this.method = method;

        String params = "app_key=" + appkey + "&customerId=" + cuid + "&format=" + format + "&method=" + method +
                "&sign_method=" + sign_method + "&timestamp=" + URLEncoder.encode(timestamp, "UTF-8") + "&v=" + v +
                "&sign=" + getSign(secret, cuid, appkey, body);
        logger.debug(url + "?" + params);
        logger.debug(body);

        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(10000);
        requestBuilder = requestBuilder.setConnectionRequestTimeout(60000);
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());
        HttpClient client = builder.build();

        HttpPost post = new HttpPost(url + "?" + params);
        HttpEntity entity = new StringEntity(body, "UTF-8");
        post.setEntity(entity);

        try {

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == SUCCESS_CODE) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.debug(result);
                return result;
            } else {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.debug(result);
                return result;
            }
        } catch (Exception e) {
            logger.error("调用奇门接口出错:", e);
        }

        return null;
    }

    private String getSign(String secr, String cuid, String appkey, String body) {
        String signString = secr + "app_key" + appkey + "customerId" + cuid + "format" + format
                + "method" + method + "sign_method" + sign_method + "timestamp" + timestamp + "v" + v + body + secr;
        return Md5Utils.hash(signString);
    }

}
