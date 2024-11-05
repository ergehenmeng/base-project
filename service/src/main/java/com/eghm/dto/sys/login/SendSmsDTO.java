package com.eghm.dto.sys.login;

import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/20 10:50
 */
@Data
public class SendSmsDTO {

    @Mobile
    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

}
