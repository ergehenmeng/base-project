package com.eghm.dto.operate.help;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpQueryDTO {

    @Schema(description = "帮助说明类型:system_dict表中help_type字段", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "帮助分类不能为空")
    private Integer helpType;

    @Schema(description = "关键字搜索(问或答)")
    private String queryName;
}
