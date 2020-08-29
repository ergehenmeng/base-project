package com.eghm.model.dto.user;

import com.eghm.model.annotation.BackstageTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/8/29
 */
@Data
public class BindEmailRequest {

    /**
     * 邮箱号
     */
    @ApiModelProperty("绑定的邮箱号")
    private String email;

    /**
     * 邮箱验证码
     */
    @ApiModelProperty("邮箱验证码")
    private String authCode;

    /**
     * 用户id
     */
    @BackstageTag
    private Integer userId;
}
