package com.eghm.model.dto.business.homestay.room.config;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * 房型设置
 * @author 二哥很猛
 * @date 2022/6/29
 */
@Data
public class RoomConfigRequest {

    @ApiModelProperty("房型id")
    @NotNull(message = "房型id不能为空")
    private Long roomId;

    @ApiModelProperty("开始日期 yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("截止日期 yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty("周期")
    @NotEmpty(message = "请选择周期")
    public List<Integer> week;

    @ApiModelProperty("状态 false:不可用 true:可用")
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @ApiModelProperty("库存不能为空")
    @Max(value = 9999, message = "最大库存9999")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    @ApiModelProperty("划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @ApiModelProperty("销售价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;
}
