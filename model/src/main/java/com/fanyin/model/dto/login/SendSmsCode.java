package com.fanyin.model.dto.login;

import com.fanyin.model.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/20 10:50
 */
@Data
public class SendSmsCode implements Serializable {

    private static final long serialVersionUID = -9143167876516780812L;

    /**
     * 手机号
     */
    @Mobile(required = true)
    @ApiModelProperty("手机号码")
    private String mobile;
}
