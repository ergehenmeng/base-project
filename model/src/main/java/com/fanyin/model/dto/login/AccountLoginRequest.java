package com.fanyin.model.dto.login;

import com.fanyin.model.validation.annotation.RangeLength;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/19 16:55
 */
@Data
public class AccountLoginRequest implements Serializable {

    private static final long serialVersionUID = 2897260194026380794L;

    /**
     * 登陆账号 手机号或邮箱
     */
    @NotEmpty(message = "登陆账号不能为空")
    @ApiModelProperty(value = "手机号或邮箱",required = true)
    private String account;

    /**
     * 密码
     */
    @RangeLength(min = 32,max = 32,message = "密码格式错误")
    @ApiModelProperty(value = "密码,MD5小写加密过",required = true)
    private String pwd;

    /**
     * 登陆ip
     */
    @ApiModelProperty(hidden = true)
    private String ip;
}
