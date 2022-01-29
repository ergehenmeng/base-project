package com.eghm.model.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyb
 * @date 2022/1/28 17:18
 */
@Data
public class CaptchaResponse {

    @ApiModelProperty("图片信息 base64格式")
    private String base64;
}
