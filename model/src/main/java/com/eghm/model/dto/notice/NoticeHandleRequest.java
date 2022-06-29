package com.eghm.model.dto.notice;

import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2021/11/5 下午5:42
 */
@Data
public class NoticeHandleRequest {

    public static final byte PUBLISH = 1;
    public static final byte CANCEL_PUBLISH = 2;
    public static final byte DELETE = 3;


    @NotNull
    @ApiModelProperty(value = "公告id", required = true)
    private Long id;

    @OptionByte({PUBLISH, CANCEL_PUBLISH, DELETE})
    private Byte state;
}
