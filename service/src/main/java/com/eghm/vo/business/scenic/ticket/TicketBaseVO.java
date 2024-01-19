package com.eghm.vo.business.scenic.ticket;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门票基础信息
 *
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Data
public class TicketBaseVO {

    @ApiModelProperty("门票id")
    private Long id;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存0:为售罄")
    private Integer stock;
}
