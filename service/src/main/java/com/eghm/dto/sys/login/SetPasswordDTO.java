package com.eghm.dto.sys.login;

import com.eghm.convertor.RsaDeserializer;
import com.eghm.validation.annotation.Password;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2021/12/26 19:22
 */
@Data
public class SetPasswordDTO {

    @ApiModelProperty(value = "请求唯一ID", required = true)
    @NotBlank(message = "请求ID不能为空")
    private String requestId;

    @ApiModelProperty(value = "密码(8~20英文,字母和@#&_) rsa加密", required = true)
    @Password
    @JsonDeserialize(using = RsaDeserializer.class)
    @Expose(serialize = false)
    private String password;
}
