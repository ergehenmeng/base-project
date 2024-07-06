package com.eghm.vo.business.item.express;

import com.eghm.convertor.BigDecimalOmitEncoder;
import com.eghm.convertor.CentToYuanOmitEncoder;
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
    @JsonSerialize(using = BigDecimalOmitEncoder.class)
    private BigDecimal firstPart;

    @ApiModelProperty(value = "首件或首重的价格")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer firstPrice;

    @ApiModelProperty(value = "续重或续件")
    @JsonSerialize(using = BigDecimalOmitEncoder.class)
    private BigDecimal nextPart;

    @ApiModelProperty(value = "续重或续件的单价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer nextUnitPrice;

    @ApiModelProperty(value = "区域编号(逗号分隔)")
    private String regionCode;

    @ApiModelProperty(value = "区域名称(逗号分隔)")
    private String regionName;
}
