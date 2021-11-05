package com.eghm.model.dto.operator;

import com.eghm.model.annotation.Sign;
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
    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码", required = true)
    @NotNull(message = "原密码不能为空")
    private String oldPwd;

    /**
     * 新密码
     */
    @NotNull(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPwd;

    /**
     * 用户id
     */
    @Sign
    private Long operatorId;
}
