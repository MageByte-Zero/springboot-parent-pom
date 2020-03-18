package com.zero.provider.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <strong>
 * 日期工具类
 * </strong>
 * @Type DateUtils.java
 * @date 2018年11月6日 下午8:28:32
 * @version 1.0
 */
class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final ThreadLocal<DateFormat> YYYY_MM_DD_HH_MM_SS_df = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        }
    };

    private static final ThreadLocal<DateFormat> YYYY_MM_DD_HH_MM_SS_SSS_df = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_S);
        }
    };

    private static DateFormat getSafetyDateFormat(String pattern) {
        switch (pattern) {
            case YYYY_MM_DD_HH_MM_SS_S:
                return YYYY_MM_DD_HH_MM_SS_SSS_df.get();
            default:
                return YYYY_MM_DD_HH_MM_SS_df.get();
        }
    }

    /**
     * 将时间的long值转为指定的时间格式输出
     * @param pattern
     * @return
     */
    public static String transferLongToString(long milliSecond, String pattern) {
        DateFormat df = getSafetyDateFormat(pattern);
        Date date = new Date(milliSecond);
        return df.format(date);
    }
}
