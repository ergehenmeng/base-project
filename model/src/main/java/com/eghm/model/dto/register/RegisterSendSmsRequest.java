package com.eghm.model.dto.register;

import com.eghm.model.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/9/3 14:26
 */
@Data
public class RegisterSendSmsRequest implements Serializable {

    private static final long serialVersionUID = -8494768267600018260L;

    /**
     * 手机号码
     */
    @Mobile
    @ApiModelProperty(required = true,value = "手机号码")
    private String mobile;

}
