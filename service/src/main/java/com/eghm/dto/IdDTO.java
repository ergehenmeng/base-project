package com.eghm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用于单一id对象映射
 * 禁止在该类中额外添加其他参数,除非是全局公用字段
 *
 * @author 殿小二
 * @since 2020/8/29
 */
@Data
public class IdDTO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;
}
