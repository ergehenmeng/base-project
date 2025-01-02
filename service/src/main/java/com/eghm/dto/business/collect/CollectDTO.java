package com.eghm.dto.business.collect;

import com.eghm.enums.CollectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
public class CollectDTO {

    @Schema(description = "收藏id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收藏id不能为空")
    private Long collectId;

    @Schema(description = "收藏对象类型(1:资讯 2:公告)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收藏对象类型不能为空")
    private CollectType collectType;
}
