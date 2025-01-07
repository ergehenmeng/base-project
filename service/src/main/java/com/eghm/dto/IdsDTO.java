package com.eghm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用于单一ids对象映射
 *
 * @author 殿小二
 * @since 2024/4/1
 */
@Data
public class IdsDTO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "id不能为空")
    private List<Long> ids;
}
