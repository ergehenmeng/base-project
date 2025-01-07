package com.eghm.dto.business.merchant;

import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
@Data
public class MerchantUserEditRequest {

    @Schema(description = "id")
    @NotNull(message = "商户用户id不能为空")
    private Long id;

    @Schema(description = "昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 10, message = "昵称长度2~10位")
    private String nickName;

    @Schema(description = "登录手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile
    private String mobile;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 32, max = 32, message = "新密码格式错误")
    private String password;

    @Schema(description = "角色id列表")
    @NotEmpty(message = "请选择角色")
    private List<Long> roleIds;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;
}
