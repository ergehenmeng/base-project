package com.eghm.exception;

import com.eghm.enums.ErrorCode;

/**
 * @author 二哥很猛
 * @since 2018/1/12 16:40
 */
public class BusinessException extends SystemException {

    public BusinessException(ErrorCode error) {
        super(error);
    }

    public BusinessException(int code, String msg) {
        super(code, msg);
    }

}
