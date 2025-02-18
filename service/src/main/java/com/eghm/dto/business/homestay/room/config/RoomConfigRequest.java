package com.eghm.dto.business.homestay.room.config;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.dto.ext.AbstractDateComparator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 房型设置
 *
 * @author 二哥很猛
 * @since 2022/6/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoomConfigRequest extends AbstractDateComparator {

    @ApiModelProperty(value = "周期", required = true)
    @NotEmpty(message = "请选择周期")
    private List<Integer> week;

    @ApiModelProperty(value = "房型id", required = true)
    @NotNull(message = "房型id不能为空")
    private Long roomId;

    @ApiModelProperty(value = "开始日期 yyyy-MM-dd", required = true)
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期 yyyy-MM-dd", required = true)
    @NotNull(message = "截止日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "状态 false:不可用 true:可用", required = true)
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @ApiModelProperty(value = "库存不能为空", required = true)
    @Max(value = 9999, message = "最大库存9999")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    @ApiModelProperty("划线价")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价", required = true)
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

    @ApiModelProperty(value = "民宿id", hidden = true)
    @Assign
    private Long homestayId;
}
