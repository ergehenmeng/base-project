package com.eghm.dto.business.member;

import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/12/12
 */

@Data
public class MemberDTO {

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "昵称不能为空")
    @Length(min = 1, max = 6, message = "昵称长度最多6个字符")
    private String nickName;

    @Schema(description = "性别", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {0, 1, 2}, message = "请选择性别")
    private Integer sex;

}
