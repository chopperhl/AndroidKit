package com.chopperhl.androidkit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description:
 * Author chopperhl
 * Date 7/4/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class TimeUtil {
    public static final String FULL_DATE_PATTERN_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_STAMP = "yyyyMMddHHmmssSSS";
    public static final String MONTH_YEAR = "MMyy";
    public static final String YEAR_MONTH = "yyyy-MM";
    public static final String DATE = "dd";
    public static final String MONTH = "MM";
    public static final String YEAR = "yyyy";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN, Locale.CHINA);

    public static String getTime(Date date, String pattern) {
        String str = "";
        try {
            dateFormat.applyPattern(pattern);
            str = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String formatData(String data) {
        try {
            return getTime(dateFormat.parse(data), DEFAULT_DATE_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatData(String data, String pattern) {
        try {
            return getTime(dateFormat.parse(data), pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatData(long data) {
        return getTime(new Date(data), DEFAULT_DATE_PATTERN);
    }

    public static String formatData(long data, String pattern) {
        return getTime(new Date(data), pattern);
    }

    public static String getCurrentTime() {
        return getTime(new Date(), DEFAULT_DATE_PATTERN);
    }

    public static String getCurrentTime(String pattern) {
        return getTime(new Date(), pattern);
    }

    /**
     * 根据字符串比较时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long compareDate(String date1, String date2) {
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            return d1.getTime() - d2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
