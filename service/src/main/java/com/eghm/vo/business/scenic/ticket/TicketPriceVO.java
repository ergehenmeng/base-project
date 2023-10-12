package com.eghm.vo.business.scenic.ticket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门票基础信息
 * @author 二哥很猛
 * @date 2022/7/12
 */
@Data
public class TicketPriceVO {

    @ApiModelProperty("最低价")
    private Integer minPrice;

    @ApiModelProperty("最高价")
    private Integer maxPrice;
}
