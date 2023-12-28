package com.eghm.dto.login;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @date 2019/8/19 16:55
 */
@Data
public class AccountLoginDTO {

    @NotEmpty(message = "登陆账号不能为空")
    @ApiModelProperty(value = "手机号或邮箱",required = true)
    private String account;

    @Size(min = 32,max = 32,message = "密码格式错误")
    @ApiModelProperty(value = "密码,MD5小写加密过",required = true)
    @NotBlank(message = "密码不能为空")
    private String pwd;

    @ApiModelProperty(value = "ip", hidden = true)
    @Assign
    private String ip;

    @ApiModelProperty(value = "设备唯一编号", hidden = true)
    @Assign
    private String serialNumber;
}
