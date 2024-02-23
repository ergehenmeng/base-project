package com.eghm.dto.business.scenic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class ScenicDetailDTO {

    @ApiModelProperty(value = "景区id", required = true)
    @NotNull(message = "请选择景区")
    private Long scenicId;

    @ApiModelProperty("经度")
    private BigDecimal longitude;

    @ApiModelProperty("纬度")
    private BigDecimal latitude;
}
