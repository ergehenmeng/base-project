package com.eghm.vo.business.order.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/5/28
 */

@Data
public class ExpressLogisticsVO {

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "快递公司编码")
    private String expressCode;

    @ApiModelProperty(value = "物流信息(json)")
    private String content;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;
}
