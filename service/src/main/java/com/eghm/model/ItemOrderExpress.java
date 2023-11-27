package com.eghm.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemOrderExpress extends BaseEntity {

    @ApiModelProperty("零售订单id")
    private String itemOrderId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("快递单号")
    private String expressNo;

    @ApiModelProperty("快递公司编码")
    private String expressCode;

    @ApiModelProperty("物流信息(json)")
    private String content;

    @ApiModelProperty("备注")
    private String remark;
}
