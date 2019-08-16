package com.fanyin.common.exception;

import com.fanyin.common.enums.ErrorCodeEnum;

/**
 * 请求校验等异常
 * @author 二哥很猛
 * @date 2019/7/4 15:20
 */
public class RequestException extends SystemException {

    public RequestException(ErrorCodeEnum error) {
        super(error);
    }

    public RequestException(int code, String msg) {
        super(code, msg);
    }
}
