package com.eghm.model.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @date 2021/12/18 16:31
 */
@Data
public class MpLoginDTO {

    @ApiModelProperty("授权code码")
    @NotNull(message = "授权码不能为空")
    private String code;
}
