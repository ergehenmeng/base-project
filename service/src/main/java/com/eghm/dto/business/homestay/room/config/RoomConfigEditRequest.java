package com.eghm.dto.business.homestay.room.config;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
@Data
public class RoomConfigEditRequest {

    @ApiModelProperty(value = "房间id", required = true)
    @NotNull(message = "房型id不能为空")
    private Long roomId;

    @ApiModelProperty(value = "日期yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "日期不能为空")
    private LocalDate configDate;

    @ApiModelProperty(value = "状态 false:不可用 true:可用", required = true)
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @ApiModelProperty(value = "库存不能为空", required = true)
    @RangeInt(max = 9999, message = "最大库存9999")
    private Integer stock;

    @ApiModelProperty("划线价")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer linePrice;

    @ApiModelProperty("销售价")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

    @ApiModelProperty(value = "民宿id", hidden = true)
    @Assign
    private Long homestayId;
}
