package com.eghm.vo.login;

import com.eghm.annotation.Desensitization;
import com.eghm.enums.FieldType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthPwdResponse {

    @ApiModelProperty("手机号")
    @Desensitization(FieldType.MOBILE_PHONE)
    private String mobile;

    @ApiModelProperty("秘钥ID")
    private String secretId;
}
