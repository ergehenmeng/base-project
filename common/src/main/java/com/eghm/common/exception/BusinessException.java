package com.eghm.common.exception;

import com.eghm.common.enums.ErrorCode;

/**
 * @author 二哥很猛
 * @date 2018/1/12 16:40
 */
public class BusinessException extends SystemException {

    private static final long serialVersionUID = 860110039545282195L;

    private Object data;

    public BusinessException(ErrorCode error) {
        super(error);
    }

    public BusinessException(int code, String msg) {
        super(code, msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
