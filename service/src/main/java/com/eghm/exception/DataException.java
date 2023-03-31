package com.eghm.exception;

import com.eghm.enums.ErrorCode;

/**
 * 特殊业务异常, 用于在异常时仍旧传递给前端一部分数据,需要约定code码前端才能进行特殊处理
 * @author 二哥很猛
 * @date 2022/5/3 16:52
 */
public class DataException extends SystemException {

    private final transient Object data;

    public DataException(ErrorCode error, Object data) {
        super(error);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
