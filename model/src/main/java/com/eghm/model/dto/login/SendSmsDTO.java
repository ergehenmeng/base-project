package com.eghm.model.dto.login;

import com.eghm.model.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/20 10:50
 */
@Data
public class SendSmsDTO implements Serializable {

    private static final long serialVersionUID = -9143167876516780812L;

    @Mobile
    @ApiModelProperty(value = "手机号码",required = true)
    private String mobile;
}
