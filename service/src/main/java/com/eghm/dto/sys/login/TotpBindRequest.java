package com.eghm.dto.sys.login;

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

    @ApiModelProperty(value = "核对码", required = true)
    @NotBlank(message = "核对码不能为空")
    @Length(min = 32, max = 32, message = "核对码格式错误")
    private String secretKey;
}
