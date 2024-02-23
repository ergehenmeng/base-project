package com.eghm.dto.business.merchant;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
@Data
public class MerchantUserAddRequest {

    @ApiModelProperty(value = "昵称", required = true)
    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 10, message = "昵称长度2~10位")
    private String nickName;

    @ApiModelProperty(value = "登录手机号", required = true)
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "密码", required = true)
    @Password
    private String password;

    @ApiModelProperty("备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;

    @ApiModelProperty("角色id列表")
    private List<Long> roleIds;

    @ApiModelProperty(value = "商户ID", hidden = true)
    @Assign
    private Long merchantId;
}
