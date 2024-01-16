package com.eghm.dto.business.item.express;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/23
 */
@Data
public class ItemMergeCalcDTO {

    @ApiModelProperty("商品ID")
    private Long itemId;

    @ApiModelProperty("总数量")
    private Integer num;

    @ApiModelProperty("快递id")
    private Long expressId;

    @ApiModelProperty("快递计费方式 1:计件 2:计费")
    private Integer chargeMode;
}
