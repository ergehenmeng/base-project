package com.eghm.dto.sys.user;

import com.eghm.annotation.Assign;
import com.eghm.convertor.RsaDeserializer;
import com.eghm.validation.annotation.Password;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest {

    @ApiModelProperty(value = "旧密码(rsa加密)", required = true)
    @NotBlank(message = "旧密码不能为空")
    @JsonDeserialize(using = RsaDeserializer.class)
    private String oldPwd;

    @ApiModelProperty(value = "新密码-英文字符、数字、@#&_(rsa加密)", required = true)
    @Password(message = "新密码格式错误")
    @JsonDeserialize(using = RsaDeserializer.class)
    private String newPwd;

    @Assign
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
}
