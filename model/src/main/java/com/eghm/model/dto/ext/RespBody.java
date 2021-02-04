package com.eghm.model.dto.ext;

import com.eghm.common.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用于返回前台的结果集 json
 *
 * @author 二哥很猛
 * @date 2018/1/12 17:41
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("响应基础对象")
public class RespBody<T> {

    /**
     * 代码 默认200为成功
     */
    @ApiModelProperty("状态码,成功=200")
    private Integer code = 200;

    /**
     * 信息
     */
    @ApiModelProperty("成功或失败的信息")
    private String msg = "success";

    /**
     * 结果集 键值对或者非基本类型对象
     */
    @ApiModelProperty("成功时可能包含的数据集")
    private T data;

    private RespBody() {
    }

    private RespBody(T data) {
        this.data = data;
    }

    private RespBody(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> RespBody<T> success() {
        return new RespBody<>();
    }

    public static <T> RespBody<T> success(T data) {
        return new RespBody<>(data);
    }

    public static <T> RespBody<T> error(ErrorCode error) {
        return new RespBody<>(error.getCode(), error.getMsg());
    }

    public static <T> RespBody<T> error(int code, String msg) {
        return new RespBody<>(code, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
