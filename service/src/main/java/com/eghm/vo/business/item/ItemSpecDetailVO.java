package com.eghm.vo.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class ItemSpecDetailVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "规格名称")
    private String specValue;

}
