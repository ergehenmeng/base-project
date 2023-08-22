package com.eghm.dto.business.item.express;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/22
 */

@Data
public class ItemExpressRegionRequest {

    @ApiModelProperty(value = "首件或首重")
    @NotNull(message = "首件或首重不能为空")
    @Min(value = 0, message = "首件或首重不能小于0")
    private BigDecimal firstPart;

    @ApiModelProperty(value = "首件或首重的价格")
    @Min(value = 0, message = "首件或首重的价格不能小于0")
    @NotNull(message = "首件或首重的价格不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer firstPrice;

    @ApiModelProperty(value = "续重或续件")
    @NotNull(message = "续重或续件不能为空")
    @Min(value = 0, message = "续重或续件不能小于0")
    private BigDecimal nextPart;

    @ApiModelProperty(value = "续重或续件的单价")
    @NotNull(message = "续重或续件的单价不能为空")
    @Min(value = 0, message = "续重或续件的单价不能小于0")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer nextUnitPrice;

    @ApiModelProperty(value = "区域编号(逗号分隔)")
    @NotBlank(message = "区域编号不能为空")
    private String regionCode;

    @ApiModelProperty(value = "区域名称(逗号分隔)")
    @NotBlank(message = "区域名称不能为空")
    private String regionName;
}
