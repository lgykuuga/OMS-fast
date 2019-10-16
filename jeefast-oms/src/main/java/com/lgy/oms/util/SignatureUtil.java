package com.lgy.oms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.TreeMap;

public class SignatureUtil {

    /**
     * URL加密
     * @throws UnsupportedEncodingException
     */
    public static String encodeUri(String s) {
        String str = "";
        try {
            str = URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("encode error !");
        }
        return str;
    }

    /**
     * md5加密
     */

    public static String getMD5(String input) {

        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(input.getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }

        return result;
    }

    /**
     * 新的md5签名，首尾放secret。
     * @param params
     * @param sessionKey
     * @return
     */
    public static String md5Signature(TreeMap<String, String> params, String sessionKey) {

        StringBuffer buff = new StringBuffer();
        buff.append(sessionKey);

        StringBuffer orgin = getBeforeSign(params);
        if (orgin == null) {
            return null;
        }
        buff.append(orgin.toString());
        buff.append(sessionKey);

        System.out.println(buff.toString());
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(buff.toString().getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }

        return result;

    }

    /**
     * 新的md5签名，首尾放secret。
     *
     * @param secret 分配给您的APP_SECRET
     */

    public static String md5MiaSignature(String str, String secret) {

        StringBuffer buff = new StringBuffer();
        buff.append(str);

        buff.append(secret);

        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(buff.toString().getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }

        return result;

    }

    /**
     * 二行制转字符串
     */

    private static String byte2hex(byte[] b) {

        StringBuffer hs = new StringBuffer();
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }

        return hs.toString().toUpperCase();

    }

    /**
     * 添加参数的封装方法
     */

    private static StringBuffer getBeforeSign(TreeMap<String, String> params) {

        StringBuffer buff = new StringBuffer();
        if (params == null) {
            return null;
        }

        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            if (!"body".equals(name)) {
                buff.append(name).append(params.get(name));
            }
        }
        buff.append(params.get("body"));

        return buff;
    }
}
