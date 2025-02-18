package com.eghm.dto.business.item;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 二哥很猛
 * @since 2023/8/9
 */
@Data
public class ItemTagEditRequest {

    @Schema(description = "id不能为空", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "id不能为空")
    private String id;

    @Schema(description = "标签名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标签名称不能为空")
    @Length(min = 2, max = 8, message = "标签名称长度应为2~8字符")
    @WordChecker(message = "标签名称存在敏感词")
    private String title;

    @Schema(description = "标签图标")
    private String icon;

    @Schema(description = "状态 0:禁用 1:启用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean state;

    @Schema(description = "备注")
    private String remark;
}
