package com.eghm.model.dto.email;

import com.eghm.common.enums.EmailCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * 发送邮箱验证码
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class EmailSendCodeRequest {

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "手机号或邮箱",required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 邮箱模板类型
     */
    private EmailCode code;

}
