package com.eghm.dto.sys.login;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:55
 */
@Data
public class DoubleCheckDTO {

    @NotEmpty(message = "请求id不能为空")
    @ApiModelProperty(value = "请求id", required = true)
    private String uuid;

    @ApiModelProperty(value = "短信验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String smsCode;

    @ApiModelProperty(value = "ip", hidden = true)
    @Assign
    private String ip;
}
