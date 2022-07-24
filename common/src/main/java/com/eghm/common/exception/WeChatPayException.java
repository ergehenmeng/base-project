package com.eghm.common.exception;

import com.eghm.common.enums.ErrorCode;

/**
 * 支付解析异常
 * @author 二哥很猛
 */
public class WeChatPayException extends SystemException {

    private static final long serialVersionUID = 860110039545282195L;

    public WeChatPayException(ErrorCode error) {
        super(error);
    }

    public WeChatPayException(int code, String msg) {
        super(code, msg);
    }

}
