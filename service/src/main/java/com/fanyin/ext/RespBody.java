package com.fanyin.ext;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 用于返回前台的结果集 json
 * @author 二哥很猛
 * @date 2018/1/12 17:41
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespBody<T> implements Serializable {

    private static final long serialVersionUID = 1574813862539970945L;

    /**
     * 代码 默认200为成功
     */
    private int code = 200;
    /**
     * 信息
     */
    private String msg;

    /**
     * 结果集 键值对或者非基本类型对象
     */
    private T data;
    
    private RespBody(){
    }

    public static <T> RespBody<T> getInstance(){
        return new RespBody<>();
    }

    public static <T> RespBody<T> success(T data){
        RespBody<T> body = new RespBody<>();
        body.setData(data);
        return body;
    }

    public static <T> RespBody<T> error(ErrorCodeEnum error){
        RespBody<T> body = new RespBody<>();
        body.setCode(error.getCode());
        body.setMsg(error.getMsg());
        return body;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public RespBody<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RespBody<T> setCode(int code) {
        this.code = code;
        return this;
    }
}
