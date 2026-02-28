package com.tulinghuo.studyenglish.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yechong
 * @since 2019/9/24 19:30
 */
public class DateUtil {

    public static void main(String[] args) {
        Date date = DateUtil.strToDate("2022-09-20 00:00:00", SIMPLE_FORMAT);
        Date d1 = getMonthStart(date);
        Date d2 = getMonthEnd(d1);
        System.out.println(d1);
        System.out.println(d2);
    }

    public static final String SIMPLE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date getDateStart(Date date) {
        String str = formatDateByPattern(date, "yyyy-MM-dd");
        return strToDate(str + " 00:00:00", SIMPLE_FORMAT);
    }

    public static Date getDateEnd(Date date) {
        String str = formatDateByPattern(date, "yyyy-MM-dd");
        return strToDate(str + " 23:59:59", SIMPLE_FORMAT);
    }

    public static Date getMonthStart(Date date) {
        String str = formatDateByPattern(date, "yyyy-MM");
        return strToDate(str + "-01 00:00:00", SIMPLE_FORMAT);
    }

    public static Date getYearStart(Date date) {
        String str = formatDateByPattern(date, "yyyy");
        return strToDate(str + "-01-01 00:00:00", SIMPLE_FORMAT);
    }

    public static Date getMonthEnd(Date date) {
        date = addDays(addMonth(date, 1), -1);
        return getDateEnd(date);
    }

    public static Date dateToDate(Date date, String dateFormat) {
        String str = formatDateByPattern(date, dateFormat);
        return strToDate(str, dateFormat);
    }

    public static Date strToDate(String str, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(str);
        }
        catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String formatDateByPattern(Date date, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static long daysBetween(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    public static String dateToCronExpression(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    public static Date getWeekStartDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, num);
        return ca.getTime();
    }

    public static Date addYear(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.YEAR, num);
        return ca.getTime();
    }

    public static Date addWeeks(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.WEEK_OF_MONTH, num);
        return ca.getTime();
    }

    public static Date addDays(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);
        return ca.getTime();
    }

    public static Date addHours(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, num);
        return ca.getTime();
    }

    public static Date addMinutes(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MINUTE, num);
        return ca.getTime();
    }

    public static Date addSecond(Date date, int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.SECOND, num);
        return ca.getTime();
    }

    public static Date getFirstDayOfThisMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getLastDayOfThisMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    public static List<String> getContinueDate(Date startDate, Date endDate) {
        List<String> dateList = new ArrayList<>();
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(startDate);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endDate);
        dateList.add(formatDateByPattern(calBegin.getTime(), "yyyy-MM-dd"));
        while (endDate.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(formatDateByPattern(calBegin.getTime(), "yyyy-MM-dd"));
        }
        return dateList;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int diffDays(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int diffMinutes(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 60)));
    }

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static String agoTimeString(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        }
        else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}