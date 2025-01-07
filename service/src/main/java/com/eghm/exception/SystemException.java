package com.eghm.exception;

import com.eghm.enums.ErrorCode;
import lombok.Getter;

/**
 * 系统异常基类,不要直接使用
 *
 * @author 二哥很猛
 * @since 2018/1/12 16:39
 */
@Getter
public class SystemException extends RuntimeException {

    private final int code;

    /**
     * 构造方法
     *
     * @param error 错误类型枚举
     */
    public SystemException(ErrorCode error) {
        this(error.getCode(), error.getMsg());
    }

    SystemException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
