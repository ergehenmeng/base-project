package com.eghm.dto.sys.login;

import com.eghm.annotation.Assign;
import com.eghm.convertor.RsaDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:55
 */
@Data
public class AccountLoginDTO {

    @NotEmpty(message = "登陆账号不能为空")
    @ApiModelProperty(value = "手机号或邮箱", required = true)
    private String account;

    @ApiModelProperty(value = "密码(rsa加密)", required = true)
    @NotBlank(message = "密码不能为空")
    @JsonDeserialize(using = RsaDeserializer.class)
    private String pwd;

    @ApiModelProperty(value = "ip", hidden = true)
    @Assign
    private String ip;

    @ApiModelProperty(value = "设备唯一编号", hidden = true)
    @Assign
    private String serialNumber;
}
