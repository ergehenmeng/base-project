package com.eghm.common.exception;

import com.eghm.common.enums.ErrorCode;

/**
 * 支付解析异常
 * @author 二哥很猛
 */
public class AiliPayException extends SystemException {

    private static final long serialVersionUID = 860110039545282195L;

    public AiliPayException(ErrorCode error) {
        super(error);
    }

    public AiliPayException(int code, String msg) {
        super(code, msg);
    }

}
