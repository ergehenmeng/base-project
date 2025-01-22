package com.eghm.utils;

import static com.eghm.utils.StringUtil.isBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wyb
 * @since 2023/6/9
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class LoggerUtil {

    /**
     * 左右填充的长度
     */
    private static final int MAX_LENGTH = 20;

    /**
     * 默认填充字符
     */
    private static final String PADDING_CHAR = "=";

    /**
     * 打印日志信息
     *
     * @param msg 日志信息
     */
    public static void print(String msg) {
        print(msg, PADDING_CHAR);
    }

    /**
     * 打印日志信息
     *
     * @param msg         日志信息
     * @param paddingChar 填充的字符
     */
    public static void print(String msg, String paddingChar) {
        if (isBlank(msg)) {
            log.info("LoggerUtil.print为空");
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n");
        appendChar(builder, paddingChar);
        builder.append(" ");
        builder.append(msg);
        builder.append(" ");
        appendChar(builder, paddingChar);
        builder.append("\n");
        log.info(builder.toString());
    }

    /**
     * 填充指定长度的字符
     *
     * @param builder     builder
     * @param paddingChar 填充的字符
     */
    private static void appendChar(StringBuilder builder, String paddingChar) {
        builder.append(String.valueOf(paddingChar).repeat(MAX_LENGTH));
    }

}
