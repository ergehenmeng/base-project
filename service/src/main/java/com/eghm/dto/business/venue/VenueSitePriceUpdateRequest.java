package com.eghm.dto.business.venue;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSitePriceUpdateRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "价格")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "请输入价格")
    private Integer price;

    @ApiModelProperty(value = "状态 0:不可预定 1:可预定")
    @NotNull(message = "请选择状态")
    private Integer state;
}
