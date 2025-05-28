package com.eghm.vo.business.order.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/5/28
 */

@Data
public class ExpressLogisticsVO {

    @Schema(description = "快递单号")
    private String expressNo;

    @Schema(description = "快递公司编码")
    private String expressCode;

    @Schema(description = "物流信息(json)")
    private String content;

    @Schema(description = "订单编号")
    private String orderNo;
}
