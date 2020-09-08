package com.eghm.model.dto.register;

import com.eghm.model.annotation.BackstageTag;
import com.eghm.model.validation.annotation.Mobile;
import com.eghm.model.validation.annotation.RangeLength;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册 密码注册(3步)或验证码注册(2)
 * @author 二哥很猛
 * @date 2019/9/3 16:32
 */
@Data
public class RegisterUserDTO implements Serializable {

    private static final long serialVersionUID = 2984281019794650407L;

    /**
     * 手机号
     */
    @Mobile
    @ApiModelProperty(value = "手机号码",required = true)
    private String mobile;

    /**
     * 短信验证码
     */
    @RangeLength(required = false,min = 4,max = 6,message = "验证码格式错误")
    @ApiModelProperty(value = "短信验证码",required = true)
    private String smsCode;

    /**
     * 注册渠道
     */
    @ApiModelProperty(hidden = true)
    private String channel;

    /**
     * 注册ip
     */
    @ApiModelProperty(hidden = true)
    @BackstageTag
    private String ip;
}
