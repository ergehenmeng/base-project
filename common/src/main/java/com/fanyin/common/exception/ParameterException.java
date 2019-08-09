package com.fanyin.common.exception;

import com.fanyin.common.enums.ErrorCodeEnum;

/**
 * 参数异常类
 * @author 二哥很猛
 * @date 2018/1/17 13:42
 */
public class ParameterException extends SystemException {

    private static final long serialVersionUID = -8344713188735190270L;

    public ParameterException(ErrorCodeEnum error) {
        super(error);
    }

    public ParameterException(int code,String msg){
        super(code, msg);
    }
}
