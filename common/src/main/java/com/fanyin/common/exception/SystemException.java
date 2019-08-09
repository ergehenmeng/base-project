package com.fanyin.common.exception;

import com.fanyin.common.enums.ErrorCodeEnum;

/**
 * 系统异常基类,不要直接使用
 * @author 二哥很猛
 * @date 2018/1/12 16:39
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -2155208136300969093L;

    private int code;

    /**
     * 构造方法
     * @param error 错误类型枚举
     */
    SystemException(ErrorCodeEnum error){
        this(error.getCode(),error.getMsg());
    }
    /**
     * 构造方式
     * @param code 错误编码
     */
    private SystemException(int code){
        super();
        this.code = code;
    }

    SystemException(int code,String msg){
        super(msg);
        this.code = code;
    }

    SystemException(String msg,Throwable e){
        super(msg,e);
    }

    SystemException(Throwable e){
        super(e);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
