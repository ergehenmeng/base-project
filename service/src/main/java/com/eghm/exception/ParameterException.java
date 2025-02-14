package com.eghm.exception;

import com.eghm.enums.ErrorCode;

/**
 * 参数异常类
 *
 * @author 二哥很猛
 * @since 2018/1/17 13:42
 */
public class ParameterException extends SystemException {

    public ParameterException(ErrorCode error) {
        super(error);
    }

}
