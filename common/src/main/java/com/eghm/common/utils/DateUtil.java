package com.eghm.common.utils;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 二哥很猛
 * @date 2018/11/9 16:56
 */
@Slf4j
public class DateUtil {

    private DateUtil() {
    }

    private static final String LONG_DATE = "yyyy-MM-dd HH:mm:ss";

    private static final String SHORT_DATE = "yyyy-MM-dd";

    private static final String MIN_DATE = "yyyy-MM";

    private static final String SIMPLE_DATE = "MM-dd HH:mm:ss";

    private static final String TIMES = "HH:mm:ss";

    private static final String SHORT_DATE_LIMIT = "yyyyMMdd";

    private static final DateTimeFormatter LONG_FORMAT = DateTimeFormatter.ofPattern(LONG_DATE);

    private static final DateTimeFormatter SHORT_FORMAT = DateTimeFormatter.ofPattern(SHORT_DATE);

    private static final DateTimeFormatter MIN_FORMAT = DateTimeFormatter.ofPattern(MIN_DATE);

    private static final DateTimeFormatter TIMES_FORMAT = DateTimeFormatter.ofPattern(TIMES);

    private static final DateTimeFormatter SHORT_LIMIT_FORMAT = DateTimeFormatter.ofPattern(SHORT_DATE_LIMIT);

    private static final DateTimeFormatter SIMPLE_FORMAT = DateTimeFormatter.ofPattern(SIMPLE_DATE);

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss
     *
     * @param date date
     * @return 字符串七日
     */
    public static String formatLong(Date date) {
        return formatLong(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss
     *
     * @param date date
     * @return 字符串七日
     */
    public static String formatLong(TemporalAccessor date) {
        return LONG_FORMAT.format(date);
    }

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatShort(Date date) {
        return formatShort(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatShort(TemporalAccessor date) {
        return SHORT_FORMAT.format(date);
    }

    /**
     * 格式化日期 yyyyMMdd
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatShortLimit(Date date) {
        return formatShortLimit(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     * 格式化日期 yyyyMMdd
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatShortLimit(TemporalAccessor date) {
        return SHORT_LIMIT_FORMAT.format(date);
    }

    /**
     * 格式化日期 MM-dd HH:mm:ss
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatSimple(Date date) {
        return formatSimple(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     * 格式化日期 MM-dd HH:mm:ss
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatSimple(TemporalAccessor date) {
        return SIMPLE_FORMAT.format(date);
    }

    /**
     * 格式化日期 HH:mm:ss
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatMin(Date date) {
        return formatMin(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     * 格式化日期 HH:mm:ss
     *
     * @param date date
     * @return 字符串日期
     */
    public static String formatMin(TemporalAccessor date) {
        return MIN_FORMAT.format(date);
    }

    /**
     * 月的第一天
     * @param month 月份 yyyy-MM
     * @return 该月的第一天
     */
    public static LocalDate parseFirstDayOfMonth(String month) {
        return LocalDate.parse(month + "-01", DateTimeFormatter.ISO_DATE);
    }


    /**
     * 根据给定的字符串格式来格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式字符串
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date.toInstant().atZone(ZoneId.systemDefault()));
    }


    /**
     * 返回相对毫秒时间
     * @return 相对时间
     */
    public static long millisTime() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    }

    /**
     * 将字符串转为Date yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期格式的字符串
     * @return 日期date
     */
    public static Date parseLongJava8(String date) {
        return convertDate(LocalDateTime.parse(date, LONG_FORMAT));
    }

    /**
     * 将字符串转为Date
     *
     * @param date 日期格式的字符串 yyyy-MM-dd
     * @return 日期date
     */
    public static Date parseShort(String date) {
        return parseDate(date, SHORT_DATE);
    }

    /**
     * 将字符串转为Date
     *
     * @param date 日期格式的字符串 yyyy-MM-dd
     * @return 日期date
     */
    public static Date parseShortJava8(String date) {
        return convertDate(LocalDate.parse(date, SHORT_FORMAT));
    }


    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.warn("日期格式解析错误 date:[{}], pattern:[{}]", date, pattern, e);
            throw new ParameterException(ErrorCode.DATE_CASE_ERROR);
        }
    }



    /**
     * 相隔天数
     *
     * @param beginTime 开始时间 前面
     * @param endTime   结束时间 后面
     * @return 相隔的天数
     */
    public static long diffDay(Date beginTime, Date endTime) {
        return diffDay(convertLocalDate(beginTime), convertLocalDate(endTime));
    }

    /**
     * 相隔年
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    private static long diffYear(Temporal beginDate, Temporal endDate) {
        return ChronoUnit.YEARS.between(beginDate, endDate);
    }

    /**
     * 相隔月数
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    private static long diffMonth(Temporal beginDate, Temporal endDate) {
        return ChronoUnit.MONTHS.between(beginDate, endDate);
    }

    /**
     * 相隔天数
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    private static long diffDay(Temporal beginDate, Temporal endDate) {
        return ChronoUnit.DAYS.between(beginDate, endDate);
    }

    /**
     * 相隔小时
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    private static long diffHour(Temporal beginDate, Temporal endDate) {
        return ChronoUnit.HOURS.between(beginDate, endDate);
    }

    /**
     * 相隔分钟
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    private static long diffMinute(Temporal beginDate, Temporal endDate) {
        return ChronoUnit.MINUTES.between(beginDate, endDate);
    }

    /**
     * 相隔秒
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    private static long diffSecond(Temporal beginDate, Temporal endDate) {
        return ChronoUnit.SECONDS.between(beginDate, endDate);
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date 日期类型
     * @return 新型日期
     */
    private static LocalDateTime convertLocalDateTime(Date date) {
        return convertZonedDateTime(date).toLocalDateTime();
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转LocalDateTime
     * @param text yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String text) {
        return LocalDateTime.parse(text, LONG_FORMAT);
    }

    /**
     * Date 转 LocalDate
     *
     * @param date 日期类型
     * @return 新型日期
     */
    private static LocalDate convertLocalDate(Date date) {
        return convertZonedDateTime(date).toLocalDate();
    }

    /**
     * 将老日期格式转换为新的带有时区的时间格式
     *
     * @param date date
     * @return 时区的dateTime
     */
    private static ZonedDateTime convertZonedDateTime(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        return instant.atZone(defaultZoneId);
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime java1.8时间表示方式
     * @return Date
     */
    public static Date convertDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDate java1.8时间表示方式
     * @return Date
     */
    public static Date convertDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取几天前的时间
     *
     * @param localDateTime 时间
     * @param day           天数
     * @return 指定day的LocalDateTime
     */
    public static LocalDateTime addDays(LocalDateTime localDateTime, int day) {
        return localDateTime.minusDays(day);
    }

    public static Date addYears(Date date, int year) {
        return DateUtils.addYears(date, year);
    }

    public static Date addMonths(Date date, int month) {
        return DateUtils.addMonths(date, month);
    }

    public static Date addWeeks(Date date, int week) {
        return DateUtils.addWeeks(date, week);
    }

    public static Date addHours(Date date, int hour) {
        return DateUtils.addHours(date, hour);
    }

    public static Date addMinutes(Date date, int minute) {
        return DateUtils.addMinutes(date, minute);
    }

    public static Date addSeconds(Date date, int second) {
        return DateUtils.addSeconds(date, second);
    }

    public static Date addMilliseconds(Date date, int millisecond) {
        return DateUtils.addMilliseconds(date, millisecond);
    }

    /**
     * 日期增减多少天
     * @param date  日期
     * @param day 天数 可以为负数
     * @return 新的日期
     */
    public static Date addDays(Date date, int day) {
        return DateUtils.addDays(date, day);
    }




    /**
     * 格式化时间 HH:mm:ss
     *
     * @param date 日期
     * @return 字符串 HH:mm:ss
     */
    public static String formatHms(Date date) {
        return format(date, TIMES);
    }

    /**
     * 格式化时间 HH:mm:ss
     *
     * @param date 日期
     * @return 字符串 HH:mm:ss
     */
    public static String formatHms(LocalDateTime date) {
        return TIMES_FORMAT.format(date);
    }


    /**
     * 获取系统当前时间戳-秒数
     *
     * @return 自 1970 年 1 月 1 日 00:00:00 UTC 以来此日期表示的秒数。
     */
    public static long getSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param pattern 将当前时间格式化指定类型
     * @return 字符串
     */
    public static String getNow(String pattern) {
        return format(getNow(), pattern);
    }

    /**
     * 获得当前日期
     *
     * @return 当前时间
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * 时间戳(秒数)转换为时间
     *
     * @param timeStamp 十位数
     * @return Date类型
     */
    public static Date timeToDate(long timeStamp) {
        Date date = new Date();
        date.setTime(timeStamp * 1000);
        return date;
    }


    /**
     * 获取指定时间的月的第一天 00:00:00
     *
     * @param now 指定时间
     * @return 日期
     */
    public static Date firstDayOfMonth(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间的月最后一天 23:59:59
     *
     * @param now 时间
     * @return 日期
     */
    public static Date lastDayOfMonth(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 指定时间的凌晨
     */
    public static Date beginOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 指定时间的最后一秒
     */
    public static Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 解析iso格式的日期
     * @param dateTime yyyy-MM-dd'T'HH:mm:ss+08:00
     * @return 日期
     */
    public static LocalDateTime parseIso(String dateTime) {
        if (StrUtil.isBlank(dateTime)) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * 将日期格式转换为中文表达
     * @param date 日期
     * @return 中文表示
     */
    public static String chineseValue(Date date) {
        LocalDateTime dateTime = convertLocalDateTime(date);
        LocalDateTime nowTime = LocalDateTime.now();
        if (diffSecond(dateTime, nowTime) < 60) {
            return "刚刚";
        }
        long minute = diffMinute(dateTime, nowTime);
        if (minute < 60) {
            return minute + "分钟前";
        }
        long day = diffDay(dateTime, nowTime);
        // 可能会涉及跨天,因此先计算时昨天还是今天
        if (day < 1) {
            long hour = diffHour(dateTime, nowTime);
            return hour + "小时前";
        }
        long month = diffMonth(dateTime, nowTime);
        if (month < 1) {
            return day + "天前";
        }
        long year = diffYear(dateTime, nowTime);
        if (year < 1) {
            return day + "月前";
        }
        return year + "年前";
    }


}
