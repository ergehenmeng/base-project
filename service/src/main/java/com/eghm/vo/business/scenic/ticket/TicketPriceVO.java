package com.eghm.vo.business.scenic.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 门票基础信息
 *
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Data
public class TicketPriceVO {

    @Schema(description = "最低价")
    private Integer minPrice;

    @Schema(description = "最高价")
    private Integer maxPrice;
}
