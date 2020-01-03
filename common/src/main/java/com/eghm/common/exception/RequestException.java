package com.eghm.common.exception;

import com.eghm.common.enums.ErrorCode;

/**
 * 请求校验等异常
 * @author 二哥很猛
 * @date 2019/7/4 15:20
 */
public class RequestException extends SystemException {

    public RequestException(ErrorCode error) {
        super(error);
    }

    public RequestException(int code, String msg) {
        super(code, msg);
    }
}
