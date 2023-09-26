package com.eghm.dto.business.merchant;

import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
@Data
public class MerchantUserEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "商户用户id不能为空")
    private Long id;

    @ApiModelProperty(value = "昵称", required = true)
    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 10, message = "昵称长度2~10位")
    private String nickName;

    @ApiModelProperty(value = "登录手机号", required = true)
    @Mobile
    private String mobile;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 16, message = "密码长度8~16位")
    private String password;

}
