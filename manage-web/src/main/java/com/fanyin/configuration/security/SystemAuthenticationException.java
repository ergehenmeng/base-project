package com.fanyin.configuration.security;

import com.fanyin.common.enums.ErrorCode;
import org.springframework.security.core.AuthenticationException;

/**
 * 自定义权限校验失败异常处理类
 *
 * @author 二哥很猛
 * @date 2018/1/26 09:44
 */
public class SystemAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 645673408616288144L;

    private int code;

    private SystemAuthenticationException(String msg) {
        super(msg);
    }

    SystemAuthenticationException(ErrorCode codeEnum) {
        this(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
