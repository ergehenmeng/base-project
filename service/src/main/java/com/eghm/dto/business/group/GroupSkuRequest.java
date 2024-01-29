package com.eghm.dto.business.group;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class GroupSkuRequest {

    @ApiModelProperty("skuId")
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @ApiModelProperty("销售价")
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @ApiModelProperty("拼团价")
    @NotNull(message = "拼团价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer groupPrice;
}
