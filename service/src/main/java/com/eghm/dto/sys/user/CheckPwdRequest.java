package com.eghm.dto.sys.user;

import com.eghm.convertor.RsaDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2022/9/17
 */
@Data
public class CheckPwdRequest {

    @ApiModelProperty(value = "密码(rsa加密)", required = true)
    @NotBlank(message = "密码不能为空")
    @JsonDeserialize(using = RsaDeserializer.class)
    @Expose(serialize = false)
    private String pwd;
}
