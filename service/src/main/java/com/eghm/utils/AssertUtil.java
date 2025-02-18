package com.eghm.utils;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 二哥很猛
 * @since 2023/11/23
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertUtil {

    /**
     * 判断对象是否为空
     *
     * @param obj       对象
     * @param errorCode 错误信息
     * @param message   日志信息
     * @param arg1      日志参数1
     */
    public static void assertNotNull(Object obj, ErrorCode errorCode, String message, Object arg1) {
        if (obj == null) {
            log.error("{} [{}]", message, arg1);
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param obj       对象
     * @param errorCode 错误信息
     * @param message   日志信息
     * @param arg1      日志参数1
     * @param arg2      日志参数2
     */
    public static void assertNotNull(Object obj, ErrorCode errorCode, String message, Object arg1, Object arg2) {
        if (obj == null) {
            log.error("{} [{}] [{}]", message, arg1, arg2);
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param obj       对象
     * @param errorCode 错误信息
     * @param message   日志信息
     * @param arg1      日志参数1
     * @param arg2      日志参数2
     * @param arg3      日志参数3
     */
    public static void assertNotNull(Object obj, ErrorCode errorCode, String message, Object arg1, Object arg2, Object arg3) {
        if (obj == null) {
            log.error("{} [{}] [{}] [{}]", message, arg1, arg2, arg3);
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 校验订单是否存在
     *
     * @param obj  订单信息
     * @param arg1 参数1
     * @param arg2 参数2
     */
    public static void assertOrderNotNull(Object obj, Object arg1, Object arg2) {
        if (obj == null) {
            log.info("订单信息不能为空 [{}] [{}]", arg1, arg2);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
    }
}
