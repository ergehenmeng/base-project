package com.eghm.vo.business.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/12
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessDetailVO {

    @ApiModelProperty("待发货订单")
    private Integer readyNum = 0;

    @ApiModelProperty("退款数量中")
    private Integer refundNum = 0;

    @ApiModelProperty("待核销订单")
    private Integer verifyNum = 0;

}
