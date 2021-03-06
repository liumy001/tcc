/**
 * @FileName: DateUtil.java
 * @Package com.asura.framework.util
 * @author zhangshaobin
 * @created 2012-11-5 下午5:53:07
 * <p>
 * Copyright 2011-2015 asura
 */
package com.eric.demo.commons.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 日期工具类
 * @author liumingyue
 * @created 2017年12月19日 下午6:58:20
 */
public class DateUtil {

    public enum IntervalUnit {
        MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
    }

    private static final Map<String, ThreadLocal<SimpleDateFormat>> timestampFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final Map<String, ThreadLocal<SimpleDateFormat>> dateFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final Object timestampFormatLock = new Object();

    private static final Object dateFormatLock = new Object();

    private static String dateFormatPattern = "yyyy-MM-dd";

    private static String timestampPattern = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat getDateFormat() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(dateFormatPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(dateFormatPattern);
                        }
                    };
                    dateFormatPool.put(dateFormatPattern, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getDateFormat(final String dateFormatPattern) {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(dateFormatPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(dateFormatPattern);
                        }
                    };
                    dateFormatPool.put(dateFormatPattern, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getTimestampFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(timestampPattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(timestampPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampPattern);
                        }
                    };
                    timestampFormatPool.put(timestampPattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 时间戳格式
     */
    //	private static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式
     */
    //	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    /**
     *
     * 根据日期格式解析成对应的date
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param str
     * @param dateFormatPattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String str, String dateFormatPattern) throws ParseException {
        SimpleDateFormat sdf = getDateFormat(dateFormatPattern);
        Date d = sdf.parse(str);
        return d;
    }

    /**
     *
     * 格式化成时间戳格式
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date    要格式化的日期
     * @return    "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     */
    public static String timestampFormat(final Date date) {
        if (date == null) {
            return "";
        }
        //		return timestampFormat.format(date);
        return getTimestampFormat().format(date);
    }

    /**
     *
     * 格式化成时间戳格式
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param datetime    要格式化的日期
     * @return    "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     */
    public static String timestampFormat(final long datetime) {
        //		return timestampFormat.format(new Date(datetime));
        return getTimestampFormat().format(new Date(datetime));
    }

    /**
     *
     * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成Long型日期
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param timestampStr    年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     * @return Long型日期
     */
    public static long formatTimestampToLong(final String timestampStr) {
        Date date;
        try {
            //			date = timestampFormat.parse(timestampStr);
            date = getTimestampFormat().parse(timestampStr);
        } catch (final ParseException e) {
            return 0L;
        }
        return date.getTime();
    }

    /**
     *
     * 格式化成日期格式
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date    要格式化的日期
     * @return    "年年年年-月月-日日"格式的日期字符串
     */
    public static String dateFormat(final Date date) {
        if (date == null) {
            return "";
        }
        //		return dateFormat.format(date);
        return getDateFormat().format(date);
    }

    /**
     *
     * 格式化成日期格式, 根据传过来的日志格式
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date
     * @param dateFormatPattern
     * @return
     */
    public static String dateFormat(final Date date, String dateFormatPattern) {
        if (date == null) {
            return "";
        }
        //		return dateFormat.format(date);
        return getDateFormat(dateFormatPattern).format(date);
    }

    /**
     *
     * 格式化成日期格式
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param datetime    要格式化的日期
     * @return    "年年年年-月月-日日"格式的日期字符串
     */
    public static String dateFormat(final long datetime) {
        //		return dateFormat.format(new Date(datetime));
        return getDateFormat().format(new Date(datetime));
    }

    /**
     *
     * 将"年年年年-月月-日日"格式的日期字符串转换成Long型日期
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param dateStr    "年年年年-月月-日日"格式的日期字符串
     * @return Long型日期
     */
    public static long formatDateToLong(final String dateStr) throws Exception {
        Date date;
        try {
            //date = dateFormat.parse(dateStr);

            date = getDateFormat().parse(dateStr);
        } catch (final ParseException e) {
            throw new Exception("将输入内容格式化成日期类型时出错。", e);
        }
        return date.getTime();
    }

    /**
     *
     * 得到本月的第一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @return 以"年年年年-月月-日日"格式返回当前月第一天的日期
     */
    public static String getFirstDayOfCurrentMonth() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        //        return dateFormat.format(calendar.getTime());
        return getDateFormat().format(calendar.getTime());
    }

    /**
     *
     * 得到月份第一天.以当前月份为基准
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param offset
     * @return Date
     */
    public static Date getFirstDayOfMonth(int offset) {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, offset);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 得到下个月的第一秒
     * @return
     */
    public static Long getFirstMillSecondOfNextMonth() {
        Date d = getFirstDayOfMonth(1);
        return d.getTime();
    }

    /**
     * 得到下个月的最后一秒
     * @return
     */
    public static Long getLastMillSecondOfNextMonth() {
        Date d = getLastDayOfMonth(1);
        return d.getTime() + 24 * 3600 * 1000L - 1000L;
    }

    /**
     *
     * 得到月份最后一天.以当前月份为基准
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @return 以"年年年年-月月-日日"格式返回当前月最后一天的日期
     */
    public static Date getLastDayOfMonth(int offset) {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, offset);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     *
     * 得到本月的最后一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @return 以"年年年年-月月-日日"格式返回当前月最后一天的日期
     */
    public static String getLastDayOfCurrentMonth() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //        return dateFormat.format(calendar.getTime());
        return getDateFormat().format(calendar.getTime());
    }

    /**
     *
     * 获取指定日期所在月的第一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date    日期
     * @return 以"年年年年-月月-日日"格式返回当指定月第一天的日期
     */
    public static String getFirstDayOfMonth(final Date date) {
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        //		return dateFormat.format(ca.getTime());
        return getDateFormat().format(ca.getTime());
    }

    /**
     *
     * 获取指定日期所在月的最后一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date
     * @return 以"年年年年-月月-日日"格式返回当指定月最后一天的日期
     */
    public static String getLastDayOfMonth(final Date date) {
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.roll(Calendar.DAY_OF_MONTH, -1);
        //		return dateFormat.format(ca.getTime());
        return getDateFormat().format(ca.getTime());
    }

    /**
     *
     * 获取指定日期所在月的最后一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date
     * @return 以"年年年年-月月-日日"格式返回当指定月最后一天的日期
     */
    public static Long getLastDayTimeOfMonth(final Date date) {
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.roll(Calendar.DAY_OF_MONTH, -1);
        //		return dateFormat.format(ca.getTime());
        return ca.getTime().getTime();
    }

    /**
     *
     * 获取指定日期所在周的第一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date    日期
     * @return 以"年年年年-月月-日日"格式返回当指定周第一天的日期
     */
    public static String getFirstDayOfWeek(final Date date) {
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_WEEK, 2);
        //		return dateFormat.format(ca.getTime());
        return getDateFormat().format(ca.getTime());
    }

    /**
     * 获取当前日期所在周的周末
     * @param date
     * @return
     */
    private static Calendar lastDayOfWeek(final Date date) {
        final Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        //日期减去1防止是周日（国外周日为一周的第一天）
        ca.add(Calendar.DATE, -1);
        //设置为本周的周六,这里不能直接设置为周日，中国本周日和国外本周日不同
        ca.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        //日期往前推移一天
        ca.add(Calendar.DATE, 1);
        return ca;
    }

    /**
     *
     * 获取当前日期所在周的周末
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date
     * @return 以"年年年年-月月-日日"
     */
    public static String getLastDayOfWeek(final Date date) {
        final Calendar ca = lastDayOfWeek(date);
        return getDateFormat().format(ca.getTime());
    }

    /**
     * 获取当前日期所在的周六    第一秒
     * @param date
     * @return
     */
    public static Long getSaturdayOfWeek(final Date date) {
        //周日零点零分零秒
        final Calendar ca = lastDayOfWeek(date);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        //日期减去1天 变为周六零时零分零秒
        ca.add(Calendar.DATE, -1);
        return ca.getTime().getTime();
    }

    /**
     * 获取当前日期所在的周日    最后一秒
     * @param date
     * @return
     */
    public static Long getLastSecondOfWeek(final Date date) {
        final Calendar ca = lastDayOfWeek(date);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        ca.add(Calendar.DATE, 1);
        return ca.getTime().getTime() - 1;
    }

    /**
     *
     * 获取当前日期的前一天
     *
     * @author zhangshaobin
     * @created 2013-3-22 上午8:58:12
     *
     * @return 以"年年年年-月月-日日"格式返回当前日期的前一天的日期
     */
    public static String getDayBeforeCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        //		return dateFormat.format(calendar.getTime());
        return getDateFormat().format(calendar.getTime());
    }

    /**
     *
     * 获取指定日期的前一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param date
     * @return 以"年年年年-月月-日日"格式返回指定日期的前一天的日期
     */
    public static String getDayBeforeDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        //		return dateFormat.format(calendar.getTime());
        return getDateFormat().format(calendar.getTime());
    }

    /**
     *
     * 获取当前日期的后一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @return 以"年年年年-月月-日日"格式返回当前日期的后一天的日期
     */
    public static String getDayAfterCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        //		return dateFormat.format(calendar.getTime());
        return getDateFormat().format(calendar.getTime());
    }

    /**
     *
     * 获取当前日期的后一天
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @return 以"年年年年-月月-日日"格式返回指定日期的后一天的日期
     */
    public static String getDayAfterDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        //		return dateFormat.format(calendar.getTime());
        return getDateFormat().format(calendar.getTime());
    }

    /**
     *
     * 获取当前时间，精确到秒
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @return 精确到秒的当前时间
     */
    public static int currentTimeSecond() {
        return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
    }

    /**
     *
     * 替换掉日期格式中所有分隔符（含“-”、“:”及空格）
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param target    字符型目标日期
     * @return 替换后的结果
     */
    public static String replaceAllSeparator(final String target) {
        return target.replace("-", "").replace(":", "").replace(" ", "");
    }

    /**
     *
     * 替换掉日志格式中指定的分隔符
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param target    字符型目标日期
     * @param separator    要被替换掉的分割符
     * @return 替换后的结果
     */
    public static String replaceSeparator(final String target, final String... separator) {
        String temp = target;
        for (final String sep : separator) {
            temp = temp.replace(sep, "");
        }
        return temp;
    }

    /**
     *
     * 根据步长获取时间
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param interval 步长 ，正数获取将来时间， 负数获取以前的时间
     * @param unit 步长单位, 年月周日时分秒
     * @return
     */
    public static Date intervalDate(final int interval, final IntervalUnit unit) {
        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.setLenient(true);
        c.add(translate(unit), interval);
        return c.getTime();
    }

    private static int translate(final IntervalUnit unit) {
        switch (unit) {
            case DAY:
                return Calendar.DAY_OF_YEAR;
            case HOUR:
                return Calendar.HOUR_OF_DAY;
            case MINUTE:
                return Calendar.MINUTE;
            case MONTH:
                return Calendar.MONTH;
            case SECOND:
                return Calendar.SECOND;
            case MILLISECOND:
                return Calendar.MILLISECOND;
            case WEEK:
                return Calendar.WEEK_OF_YEAR;
            case YEAR:
                return Calendar.YEAR;
            default:
                throw new IllegalArgumentException("Unknown IntervalUnit");
        }
    }

    /**
     * 获取几天前或几天后的日期
     *
     * @param day
     *            可为负数,为负数时代表获取之前的日期.为正数,代表获取之后的日期
     * @return
     */
    public static Date getTime(final int day) {
        return getTime(new Date(), day);
    }

    /**
     * 获取指定日期几天前或几天后的日期
     *
     * @param date
     *            指定的日期
     * @param day
     *            可为负数, 为负数时代表获取之前的日志.为正数,代表获取之后的日期
     * @return
     */
    public static Date getTime(final Date date, final int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
        return calendar.getTime();
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile(
                "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * 获取小时之前的时间串
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param hour 小时
     * @return
     */
    public static String getTimeStringOfHourBefore(int hour) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, hour * -1);
        return getTimestampFormat().format(calendar.getTime());
    }

    /**
     *
     * 获取两个日期之间的天数
     * 例如：startDate=2016-04-03   endDate=2016-04-05
     *     返回：2
     *
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDatebetweenOfDayNum(Date startDate, Date endDate) {
        try {
            startDate = parseDate(getDateFormat().format(startDate.getTime()), dateFormatPattern);
            endDate = parseDate(getDateFormat().format(endDate.getTime()), dateFormatPattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取两个日期间的区间日期
     * 例如：startDate=2016-04-03   endDate=2016-04-05
     *     返回：2016-04-03  2016-04-04  2016-04-05  集合
     * @author liumingyue
     * @created 2017年12月19日 下午6:58:20
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> getDatebetweenOfDays(Date startDate, Date endDate) {
        List<Date> list = new ArrayList<Date>();
        GregorianCalendar gc1 = new GregorianCalendar(), gc2 = new GregorianCalendar();
        gc1.setTime(startDate);
        gc2.setTime(endDate);
        do {
            GregorianCalendar gc3 = (GregorianCalendar) gc1.clone();
            list.add(gc3.getTime());
            System.out.println(gc3.getTime());
            gc1.add(Calendar.DAY_OF_MONTH, 1);
        } while (!gc1.after(gc2));
        return null;
    }

    /****
     * 传入具体日期 ，返回具体日期减一个月。
     *
     * @param date
     *            日期(2014-04-20)
     * @return 2014-03-20
     * @throws ParseException
     */
    public static String subMonth(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);

        rightNow.add(Calendar.MONTH, -1);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);

        return reStr;
    }

    /****
     * 获取月末最后一天
     *
     * @param sDate
     *            2014-11-24
     * @return 30
     */
    private static String getMonthMaxDay(String sDate) {
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf_full.parse(sDate + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        int last = cal.getActualMaximum(Calendar.DATE);
        return String.valueOf(last);
    }

    // 判断是否是月末
    public static boolean isMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DATE) == cal
                .getActualMaximum(Calendar.DAY_OF_MONTH))
            return true;
        else
            return false;
    }

    /***
     * 日期减一天、加一天
     *
     * @param option
     *            传入类型 pro：日期减一天，next：日期加一天
     * @param _date
     *            2014-11-24
     * @return 减一天：2014-11-23或(加一天：2014-11-25)
     */
    public static String checkOption(String option, String _date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        Date date = null;

        try {
            date = (Date) sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);
        if ("pre".equals(option)) {
            // 时间减一天
            cl.add(Calendar.DAY_OF_MONTH, -1);
        } else if ("next".equals(option)) {
            // 时间加一天
            cl.add(Calendar.DAY_OF_YEAR, 1);
        } else {
            // do nothing
        }
        date = cl.getTime();
        return sdf.format(date);
    }

    /***
     * 判断日期是否为当前月， 是当前月返回当月最小日期和当月目前最大日期以及传入日期上月的最大日和最小日
     * 不是当前月返回传入月份的最大日和最小日以及传入日期上月的最大日和最小日
     *
     * @param date
     *            日期 例如：2014-11
     * @return String[] 开始日期，结束日期，上月开始日期，上月结束日期
     * @throws ParseException
     */
    public static String[] getNow_Pre_Date(String date) throws ParseException {

        String[] str_date = new String[4];
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd");
        String stMonth = sdf.format(now);
        String stdate = "";// 开始日期
        String endate = "";// 结束日期
        String preDate_start = "";// 上月开始日期
        String preDate_end = "";// 上月结束日期

        // 当前月
        if (date.equals(stMonth)) {
            stdate = stMonth + "-01"; // 2014-11-01
            endate = sdf_full.format(now);// 2014-11-24
            preDate_start = subMonth(stdate);// 2014-10-01
            preDate_end = subMonth(endate);// 2014-10-24
        } else {
            // 非当前月
            String monthMaxDay = getMonthMaxDay(date);
            stdate = date + "-01";// 2014-10-01
            endate = date + "-" + monthMaxDay;// 2014-10-31
            preDate_start = subMonth(stdate);// 2014-09-01
            preDate_end = subMonth(endate);// 2014-09-30
        }
        str_date[0] = stdate;
        str_date[1] = endate;
        str_date[2] = preDate_start;
        str_date[3] = preDate_end;

        return str_date;
    }

    /**
     * //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
     * d: Calendar.MONDAY  //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
     * @param n
     * @return
     */
    public static String getWeek(int n, int d) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, n * 7);

        cal.set(Calendar.DAY_OF_WEEK, d);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public static Map<String, Object> convertWeekByDate(Date time) {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);

        map.put("first", imptimeBegin);

        map.put("last", imptimeEnd);

        return map;
    }




    public static void main(String[] args) throws ParseException {
        System.out.println(convertWeekByDate(new Date()).get("last"));
    }


}
