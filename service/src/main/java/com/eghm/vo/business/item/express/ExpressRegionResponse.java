package com.eghm.vo.business.item.express;

import com.eghm.convertor.BigDecimalOmitSerializer;
import com.eghm.convertor.CentToYuanOmitSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */

@Data
public class ExpressRegionResponse {

    @Schema(description = "id")
    private Long regionId;

    @Schema(description = "首件或首重")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal firstPart;

    @Schema(description = "首件或首重的价格")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer firstPrice;

    @Schema(description = "续重或续件")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal nextPart;

    @Schema(description = "续重或续件的单价")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer nextUnitPrice;

    @Schema(description = "区域编号(逗号分隔)")
    private String regionCode;

    @Schema(description = "区域名称(逗号分隔)")
    private String regionName;
}
