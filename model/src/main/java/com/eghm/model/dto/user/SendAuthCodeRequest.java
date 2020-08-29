package com.eghm.model.dto.user;

import com.eghm.common.enums.EmailType;
import com.eghm.model.annotation.BackstageTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class SendAuthCodeRequest {

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "手机号或邮箱",required = true)
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 用户id
     */
    @BackstageTag
    private Integer userId;

    /**
     * 邮件模板类型
     */
    @BackstageTag
    private EmailType type;
}
