package com.eghm.dto.register;

import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/9/3 14:26
 */
@Data
public class RegisterSmsDTO {

    @Mobile
    @ApiModelProperty(required = true, value = "手机号码")
    private String mobile;

}
