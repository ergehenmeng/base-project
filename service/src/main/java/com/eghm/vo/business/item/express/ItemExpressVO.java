package com.eghm.vo.business.item.express;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/24
 */
@Data
public class ItemExpressVO {

    @ApiModelProperty("零售Id")
    private Long itemId;

    @ApiModelProperty("物流模板Id")
    private Long expressId;

    @ApiModelProperty("物流计费方式 1:计件 2:计时")
    private Integer chargeMode;
}
