package com.eghm.dto.operator;

import com.eghm.annotation.Padding;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest implements Serializable {

    private static final long serialVersionUID = 1608188266129161564L;

    @ApiModelProperty(value = "旧密码", required = true)
    @NotNull(message = "原密码不能为空")
    private String oldPwd;

    @NotNull(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPwd;

    @Padding
    private Long operatorId;
}
