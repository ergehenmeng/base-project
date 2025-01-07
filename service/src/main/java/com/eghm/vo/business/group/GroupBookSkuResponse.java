package com.eghm.vo.business.group;

import com.eghm.convertor.CentToYuanOmitEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/8/5
 */

@Data
public class GroupBookSkuResponse {

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer salePrice;

    @Schema(description = "限时价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer discountPrice;
}
