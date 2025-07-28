package com.eghm.dto.sys.login;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2022/1/28 17:26
 */
@Data
public class TotpBindRequest {

    @ApiModelProperty(value = "序列号", required = true)
    @NotBlank(message = "序列号不能为空")
    private String uuid;

    @ApiModelProperty(value = "秘钥", required = true)
    @NotBlank(message = "秘钥不能为空")
    @Length(min = 32, max = 32, message = "秘钥格式错误")
    private String secretKey;

    @ApiModelProperty(value = "动态口令", required = true)
    @RangeInt(max = 999999, message = "动态口令不合法")
    private Integer verifyCode;
}
