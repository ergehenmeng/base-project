package com.eghm.exception;

import com.eghm.enums.ErrorCode;

/**
 * @author 二哥很猛
 * @since 2023/9/22
 */
public class WeChatPayException extends SystemException {

    public WeChatPayException(ErrorCode error) {
        super(error);
    }
}
