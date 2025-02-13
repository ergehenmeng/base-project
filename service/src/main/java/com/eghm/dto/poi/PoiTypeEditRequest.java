package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class PoiTypeEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 20, message = "类型名称最大20字符")
    @NotBlank(message = "类型名称不能为空")
    @WordChecker(message = "类型名称包含敏感词")
    private String title;

    @Schema(description = "区域", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择区域")
    private String areaCode;

    @Schema(description = "icon", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "icon不能为空")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;
}
