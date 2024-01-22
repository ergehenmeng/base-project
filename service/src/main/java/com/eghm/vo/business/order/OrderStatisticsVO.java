package com.eghm.vo.business.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatisticsVO {

    @ApiModelProperty("下单日期")
    private LocalDate createDate;

    @ApiModelProperty("订单数量")
    private Integer orderNum;

    @ApiModelProperty("支付金额")
    private Integer payAmount;
}
