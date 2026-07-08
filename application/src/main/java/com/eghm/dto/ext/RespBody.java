package com.eghm.dto.ext;

import com.eghm.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于返回前台的结果集 json
 *
 * @author 二哥很猛
 * @since 2018/1/12 17:41
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespBody<T> {

    @Schema(description = "状态码,成功=200")
    private Integer code = ErrorCode.SUCCESS.getCode();

    @Schema(description = "成功或失败的信息")
    private String msg = "success";

    @Schema(description = "成功时可能包含的数据集")
    private T data;

    private RespBody(T data) {
        this.data = data;
    }

    private RespBody(Integer code, String msg) {
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

    public static <T> RespBody<T> error(ErrorCode error, Object... args) {
        return new RespBody<>(error.getCode(), String.format(error.getMsg(), args));
    }

    public static <T> RespBody<T> error(int code, String msg) {
        return new RespBody<>(code, msg);
    }

}
