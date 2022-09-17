package com.eghm.model.dto.operator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2022/9/17
 */
@Data
public class CheckPwdRequest {

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String pwd;
}
