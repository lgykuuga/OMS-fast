package com.lgy.system.incrementer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description ID流水生成器Bean
 * @Author LGy
 * @Date 2019/12/9
 */
public class IDIncrementerBean {
    /**
     * 原始规则
     */
    private String voru;
    /**
     * id的前缀
     */
    private String prefix;

    /**
     * 年SimpleDateFormat
     */
    private SimpleDateFormat y;

    /**
     * 月SimpleDateFormat
     */
    private SimpleDateFormat m;

    /**
     * 日SimpleDateFormat
     */
    private SimpleDateFormat d;

    /**
     * 序号的格式化format
     */
    private String idFormat;

    /**
     * 记录自增的id长度
     */
    private int idlength = 0;

    public static void main(String[] args) {
        IDIncrementerBean ib = new IDIncrementerBean("OD[yyyy][mm][dd][ID00000]");
        System.out.println(ib);
    }

    /**
     * 初始化
     *
     * @param voru 格式
     */
    public IDIncrementerBean(String voru) {
        this.voru = voru;
        String v = voru;
        v = v.replace("][", ",");
        v = v.replace("[", ",");
        v = v.replace("]", "");

        String[] str = v.split(",");
        int len = 0;
        //id的前缀
        prefix = str[0];
        for (int i = 1; i < str.length; i++) {
            String group = str[i];
            if (group.toLowerCase().indexOf("yy") != -1) {
                y = new SimpleDateFormat(group.toLowerCase());
            } else if ("mm".equals(group.toLowerCase())) {
                m = new SimpleDateFormat("MM");
            } else if ("dd".equals(group.toLowerCase())) {
                d = new SimpleDateFormat("dd");
            }
        }

        len = str[str.length - 1].length() - 2;
        this.idlength = len;
        //序号
        idFormat = "%0" + len + "d";
    }

    /**
     * 获取下一个ID
     *
     * @param number 序号
     * @return String
     */
    public String getNextFormatID(long number) {
        Date now = new Date();
        StringBuffer buff = new StringBuffer();
        buff.append(prefix);
        if (y != null) {
            buff.append(y.format(now));
        }
        if (m != null) {
            buff.append(m.format(now));
        }
        if (d != null) {
            buff.append(d.format(now));
        }
        buff.append(String.format(idFormat, number));

        return buff.toString();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public SimpleDateFormat getY() {
        return y;
    }

    public void setY(SimpleDateFormat y) {
        this.y = y;
    }

    public SimpleDateFormat getM() {
        return m;
    }

    public void setM(SimpleDateFormat m) {
        this.m = m;
    }

    public SimpleDateFormat getD() {
        return d;
    }

    public void setD(SimpleDateFormat d) {
        this.d = d;
    }

    public String getIdFormat() {
        return idFormat;
    }

    public void setIdFormat(String idFormat) {
        this.idFormat = idFormat;
    }

    public int getIdlength() {
        return idlength;
    }

    public void setIdlength(int idlength) {
        this.idlength = idlength;
    }

    public String getVoru() {
        return voru;
    }

    public void setVoru(String voru) {
        this.voru = voru;
    }

}
