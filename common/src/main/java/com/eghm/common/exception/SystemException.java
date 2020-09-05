package com.eghm.common.exception;

import com.eghm.common.enums.ErrorCode;

/**
 * 系统异常基类,不要直接使用
 * @author 二哥很猛
 * @date 2018/1/12 16:39
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -2155208136300969093L;

    private final int code;

    private Object data;

    /**
     * 构造方法
     * @param error 错误类型枚举
     */
    public SystemException(ErrorCode error){
        this(error.getCode(),error.getMsg());
    }

    SystemException(int code,String msg){
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
