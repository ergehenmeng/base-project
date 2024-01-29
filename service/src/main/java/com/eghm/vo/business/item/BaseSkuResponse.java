package com.eghm.vo.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class BaseSkuResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "一级规格名(单规格为空)")
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名(单规格为空)")
    private String secondSpecValue;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;
}
