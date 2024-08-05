package com.eghm.vo.business.group;

import com.eghm.convertor.CentToYuanOmitEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/8/5
 */

@Data
public class GroupBookSkuResponse {

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("销售价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer salePrice;

    @ApiModelProperty("限时价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer discountPrice;
}
