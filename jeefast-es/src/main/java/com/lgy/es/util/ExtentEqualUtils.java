package com.lgy.es.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description
 * @Author LGy
 * @Date 2020/4/23
 */
public class ExtentEqualUtils {

    private static Logger logger = LoggerFactory.getLogger(ExtentEqualUtils.class);

    public static boolean equalsString(Object a, Object b) {
        return StringUtils.isAllBlank((String) a, (String) b) ||
                StringUtils.equals((String) a, (String) b);
    }

    public static boolean equalsBigDecimal(Object a, Object b) {

        if (isAllNull(a, b)) {
            return true;
        }

        if (isSingleNull(a, b)) {
            return false;
        }

        BigDecimal dec1 = new BigDecimal(String.valueOf(a));
        BigDecimal dec2 = (BigDecimal) b;

        return dec1.compareTo(dec2) == 0;

    }

    public static boolean equalsDate(Object a, Object b) {

        if (isAllNull(a, b)) {
            return true;
        }

        if (isSingleNull(a, b)) {
            return false;
        }

        Date date1 = null;
        try {
            if (a instanceof String) {
                String dateStr = (String) a;
                if (dateStr.length() == 13) {
                    date1 = new Date(Long.parseLong(dateStr));
                } else {
                    date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateStr.substring(0, 19));
                }
            }

            if (a instanceof Long) {
                date1 = new Date((Long) a);
            }
        } catch (ParseException e) {
            logger.warn("日期转换失败 -> {}", e);
        }
        Date date2 = (Date) b;

        return DateUtils.truncatedEquals(date1, date2, Calendar.MILLISECOND);

    }

    public static boolean equalsBoolean(Object a, Object b) {

        if (StringUtils.isAllBlank((String) a, (String) b)) {
            return true;
        }

        if (isAllN(a, b)) {
            return true;
        }

        if (isSingleNull(a, b)) {
            return false;
        }

        return StringUtils.equals((String) a, (String) b);

    }

    private static boolean isAllN(Object a, Object b) {
        return (a == null || StringUtils.isBlank(String.valueOf(a))) && "N".equals(b) || (b == null || StringUtils.isBlank(String.valueOf(b))) && "N".equals(a);
    }

    private static boolean isAllNull(Object a, Object b) {
        return (null == a || StringUtils.isBlank(String.valueOf(a))) && null == b;
    }

    private static boolean isSingleNull(Object a, Object b) {
        return null == a || StringUtils.isBlank(String.valueOf(a)) || null == b;
    }

    public static void main(String[] args) {
        Object o1 = "1";
        Object o2 = "2";
        System.out.println(StringUtils.isAllBlank((String) o1, (String) o2));
    }
}
