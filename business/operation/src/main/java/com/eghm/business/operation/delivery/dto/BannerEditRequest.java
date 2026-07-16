package com.eghm.business.operation.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2019/8/22 19:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BannerEditRequest extends BannerAddRequest {

    @Schema(description = "id不能为空", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

}
