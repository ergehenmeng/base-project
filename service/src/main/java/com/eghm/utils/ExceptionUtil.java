package com.eghm.utils;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * @author 二哥很猛
 * @since 2024/2/15
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtil {

    /**
     * 如果是指定的业务异常, 则执行runnable
     *
     * @param e 错误信息
     * @param errorCode 错误码
     * @param runnable 指定的逻辑
     */
    public static void error(Throwable e, ErrorCode errorCode, Runnable runnable) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            if (businessException.getCode() == errorCode.getCode()) {
                runnable.run();
                return;
            }
        }
        log.info("非业务异常,不做捕获操作", e);
    }

    /**
     * 如果是指定方法抛了指定的异常, 则执行runnable
     *
     * @param supplier 指定逻辑
     * @param errorCode 错误码
     * @param callable 指定的逻辑
     * @param <T> 返回值类型
     */
    public static <T> T run(Supplier<T> supplier, ErrorCode errorCode, Supplier<T> callable) {
        try {
            return supplier.get();
        } catch (BusinessException e) {
            if (e.getCode() == errorCode.getCode()) {
                return callable.get();
            }
            throw e;
        }
    }

    /**
     * 如果是指定方法抛了指定的异常, 则执行runnable
     *
     * @param runnable 指定逻辑
     * @param errorCode 错误码
     * @param callable 指定的逻辑
     */
    public static void run(Runnable runnable, ErrorCode errorCode, Runnable callable) {
        try {
            runnable.run();
        } catch (BusinessException e) {
            if (e.getCode() == errorCode.getCode()) {
                callable.run();
                return;
            }
            throw e;
        }
    }

    /**
     * 如果方法抛出了指定的异常
     *
     * @param runnable 指定逻辑
     * @param errorCode 错误码
     * @param callable 新的错误码
     */
    public static void transfer(Runnable runnable, ErrorCode errorCode, ErrorCode callable) {
        try {
            runnable.run();
        } catch (BusinessException e) {
            if (e.getCode() == errorCode.getCode()) {
                throw new BusinessException(callable);
            }
            throw e;
        }
    }
}
