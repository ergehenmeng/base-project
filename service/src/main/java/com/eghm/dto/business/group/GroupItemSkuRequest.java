package com.eghm.dto.business.group;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class GroupItemSkuRequest {

    @Schema(description = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @Schema(description = "拼团价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "拼团价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer discountPrice;
}
