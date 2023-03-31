package com.eghm.dto.business.homestay.room.config;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@Data
public class RoomConfigEditRequest {

    @ApiModelProperty("房间id")
    @NotNull(message = "房型id不能为空")
    private Long roomId;

    @ApiModelProperty("日期yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "日期不能为空")
    private LocalDate configDate;

    @ApiModelProperty("状态 false:不可用 true:可用")
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

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
