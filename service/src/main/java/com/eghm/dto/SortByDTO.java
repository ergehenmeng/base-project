package com.eghm.dto;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用于排序
 *
 * @author 殿小二
 * @since 2022/12/29
 */
@Data
public class SortByDTO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 999, message = "排序字段应为0~999之间")
    private Integer sortBy;
}
