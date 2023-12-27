package com.eghm.vo.business.item.express;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */

@Data
public class ExpressRegionResponse {

    @ApiModelProperty("id")
    private Long regionId;

    @ApiModelProperty(value = "首件或首重")
    private BigDecimal firstPart;

    @ApiModelProperty(value = "首件或首重的价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer firstPrice;

    @ApiModelProperty(value = "续重或续件")
    private BigDecimal nextPart;

    @ApiModelProperty(value = "续重或续件的单价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer nextUnitPrice;

    @ApiModelProperty(value = "区域编号(逗号分隔)")
    private String regionCode;

    @ApiModelProperty(value = "区域名称(逗号分隔)")
    private String regionName;
}
