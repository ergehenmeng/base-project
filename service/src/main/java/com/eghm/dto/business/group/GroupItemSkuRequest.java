package com.eghm.dto.business.group;

import com.eghm.convertor.YuanToCentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class GroupItemSkuRequest {

    @ApiModelProperty(value = "skuId", required = true)
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @ApiModelProperty(value = "销售价", required = true)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer salePrice;

    @ApiModelProperty(value = "拼团价", required = true)
    @NotNull(message = "拼团价不能为空")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer discountPrice;
}
