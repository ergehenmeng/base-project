package com.eghm.configuration.security;

import com.eghm.common.enums.ErrorCode;
import org.springframework.security.core.AuthenticationException;

/**
 * 自定义权限校验失败异常处理类
 *
 * @author 二哥很猛
 * @date 2018/1/26 09:44
 */
public class SystemAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 645673408616288144L;

    private final int code;

    SystemAuthenticationException(ErrorCode codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
