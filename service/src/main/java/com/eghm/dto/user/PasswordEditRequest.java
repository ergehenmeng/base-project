package com.eghm.dto.user;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest {

    @ApiModelProperty(value = "旧密码", required = true)
    @Length(min = 32, max = 32, message = "旧密码格式错误")
    private String oldPwd;

    @ApiModelProperty(value = "新密码", required = true)
    @Length(min = 32, max = 32, message = "新密码格式错误")
    private String newPwd;

    @Assign
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
}
