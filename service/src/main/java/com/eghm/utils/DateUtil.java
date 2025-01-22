package com.eghm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 * @since 2018/11/9 16:56
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    private static final String LONG_DATE = "yyyy-MM-dd HH:mm:ss";

    private static final String SIMPLE_DATE = "MM-dd HH:mm:ss";

    private static final String SHORT_DATE_LIMIT = "yyyyMMdd";

    private static final String MIN_DATE = "yyyy-MM";

    private static final int SECOND_MINUTE = 60;

    public static final DateTimeFormatter SHORT_LIMIT_FORMAT = DateTimeFormatter.ofPattern(SHORT_DATE_LIMIT);

    public static final DateTimeFormatter SIMPLE_FORMAT = DateTimeFormatter.ofPattern(SIMPLE_DATE);

    public static final DateTimeFormatter LONG_FORMAT = DateTimeFormatter.ofPattern(LONG_DATE);

    public static final DateTimeFormatter MIN_FORMAT = DateTimeFormatter.ofPattern(MIN_DATE);

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
    public static String formatSimple(TemporalAccessor date) {
        return SIMPLE_FORMAT.format(date);
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
     * yyyy-MM-dd HH:mm:ss 转LocalDateTime
     *
     * @param text yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String text) {
        return LocalDateTime.parse(text, LONG_FORMAT);
    }

    /**
     * 解析iso格式的日期
     *
     * @param dateTime yyyy-MM-dd'T'HH:mm:ss+08:00
     * @return 日期
     */
    public static LocalDateTime parseIso(String dateTime) {
        if (isBlank(dateTime)) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * 将日期格式转换为中文表达
     *
     * @param dateTime 日期
     * @return 中文表示
     */
    public static String chineseValue(LocalDateTime dateTime) {
        LocalDateTime nowTime = LocalDateTime.now();
        if (diffSecond(dateTime, nowTime) < SECOND_MINUTE) {
            return "刚刚";
        }
        long minute = diffMinute(dateTime, nowTime);
        if (minute < SECOND_MINUTE) {
            return minute + "分钟前";
        }
        long day = diffDay(dateTime, nowTime);
        // 可能会涉及跨天(即12小时前可能已经是昨天)
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
