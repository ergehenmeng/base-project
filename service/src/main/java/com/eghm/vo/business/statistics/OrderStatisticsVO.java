package com.eghm.vo.business.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@NoArgsConstructor
public class OrderStatisticsVO {

    @ApiModelProperty("下单日期")
    private LocalDate createDate;

    @ApiModelProperty("订单数量")
    private Integer orderNum = 0;

    @ApiModelProperty("支付金额")
    private Integer payAmount = 0;

    public OrderStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
    }
}
