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
