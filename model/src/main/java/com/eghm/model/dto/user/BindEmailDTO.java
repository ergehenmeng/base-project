package com.eghm.model.dto.user;

import com.eghm.model.annotation.BackstageTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author 殿小二
 * @date 2020/8/29
 */
@Data
public class BindEmailDTO {

    /**
     * 邮箱号
     */
    @ApiModelProperty(value = "绑定的邮箱号",required = true)
    @Email(message = "邮箱地址不能为空")
    private String email;

    /**
     * 邮箱验证码
     */
    @ApiModelProperty(value = "邮箱验证码",required = true)
    @Email(message = "邮箱验证码不能为空")
    private String authCode;

    /**
     * 用户id
     */
    @BackstageTag
    @ApiModelProperty(hidden = true)
    private Integer userId;
}
