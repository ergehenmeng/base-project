package com.eghm.model.dto.business.scenic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class ScenicDetailDTO {

    @ApiModelProperty("景区id")
    @NotNull(message = "景区id不能为空")
    private Long scenicId;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;
}
