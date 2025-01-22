package com.eghm.dto.business.venue;

import com.eghm.convertor.YuanToCentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSitePriceUpdateRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "价格")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "请输入价格")
    private Integer price;

    @Schema(description = "状态 0:不可预定 1:可预定", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择状态")
    private Integer state;
}
