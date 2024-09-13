package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/18
 */

@Data
public class RefundAudit {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("商户ID")
    private Long merchantId;

    @ApiModelProperty("退款id不能为空")
    private Long refundId;

    @ApiModelProperty("实际退款金额")
    private Integer refundAmount;
}
