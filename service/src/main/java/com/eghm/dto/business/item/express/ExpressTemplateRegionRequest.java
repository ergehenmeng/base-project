package com.eghm.dto.business.item.express;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/22
 */

@Data
public class ExpressTemplateRegionRequest {

    @Schema(description = "首件或首重", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "首件或首重不能为空")
    @Min(value = 0, message = "首件或首重不能小于0")
    private BigDecimal firstPart;

    @Schema(description = "首件或首重的价格", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "首件或首重的价格不能小于0")
    @NotNull(message = "首件或首重的价格不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer firstPrice;

    @Schema(description = "续重或续件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "续重或续件不能为空")
    @Min(value = 0, message = "续重或续件不能小于0")
    private BigDecimal nextPart;

    @Schema(description = "续重或续件的单价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "续重或续件的单价不能为空")
    @Min(value = 0, message = "续重或续件的单价不能小于0")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer nextUnitPrice;

    @Schema(description = "区域编号(逗号分隔)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "区域编号不能为空")
    private String regionCode;

    @Schema(description = "区域名称(逗号分隔)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "区域名称不能为空")
    private String regionName;
}
