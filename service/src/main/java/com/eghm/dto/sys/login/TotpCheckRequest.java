package com.eghm.dto.sys.login;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2025/7/18 17:26
 */
@Data
public class TotpCheckRequest {

    @ApiModelProperty(value = "序列号", required = true)
    @NotBlank(message = "序列号不能为空")
    private String uuid;

    @ApiModelProperty(value = "校验码", required = true)
    @RangeInt(min = 100000, max = 999999, message = "校验码不合法")
    private Integer verifyCode;
}
