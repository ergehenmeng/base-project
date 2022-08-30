package com.eghm.model.dto.business.homestay.room.config;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.eghm.model.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@Data
public class RoomConfigEditRequest {

    @ApiModelProperty("id")
    @NotNull
    private Long id;

    @ApiModelProperty("状态 0:不可用 1:可用")
    @NotNull(message = "是否可定不能为空")
    private Integer state;

    @ApiModelProperty("库存不能为空")
    @RangeInt(max = 9999, message = "最大库存9999")
    private Integer stock;

    @ApiModelProperty("划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @ApiModelProperty("销售价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

}
