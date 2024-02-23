package com.eghm.dto.business.line.config;

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
 * @since 2022/8/30
 */
@Data
public class LineConfigOneRequest {

    @ApiModelProperty(value = "线路id", required = true)
    @NotNull(message = "线路id不能为空")
    private Long lineId;

    @ApiModelProperty(value = "日期 yyyy-MM-dd", required = true)
    @NotNull(message = "日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate configDate;

    @ApiModelProperty(value = "状态 0:不可用 1:可用", required = true)
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @ApiModelProperty(value = "库存不能为空", required = true)
    @RangeInt(max = 9999, message = "最大库存9999")
    private Integer stock;

    @ApiModelProperty("划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价", required = true)
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

}
