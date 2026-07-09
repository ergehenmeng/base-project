package com.eghm.application.shared.dto.business.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsEditRequest extends NewsAddRequest {

    @Schema(description = "资讯id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "资讯id不能为空")
    private Long id;

}
