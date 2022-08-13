package com.eghm.model.dto.login;

import com.eghm.model.annotation.Sign;
import com.eghm.model.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/19 16:57
 */
@Data
public class SmsLoginDTO implements Serializable {

    private static final long serialVersionUID = -297158371625408459L;

    @Size(min = 4,max = 6,message = "短信验证码格式错误")
    @ApiModelProperty(value = "短信验证码4-6位",required = true)
    private String smsCode;

    @Mobile
    @ApiModelProperty(value = "手机号",required = true)
    private String mobile;

    @ApiModelProperty(value = "ip", hidden = true)
    @Sign
    private String ip;
}
