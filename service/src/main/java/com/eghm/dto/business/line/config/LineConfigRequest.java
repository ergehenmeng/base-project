package com.eghm.dto.business.line.config;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.dto.ext.AbstractDateComparator;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LineConfigRequest extends AbstractDateComparator {

    @ApiModelProperty(value = "周期", required = true)
    @NotEmpty(message = "请选择周期")
    private List<Integer> week;

    @ApiModelProperty(value = "线路id", required = true)
    @NotNull(message = "线路id不能为空")
    private Long lineId;

    @ApiModelProperty(value = "开始日期 yyyy-MM-dd", required = true)
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期 yyyy-MM-dd", required = true)
    @NotNull(message = "截止日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "状态 0:不可用 1:可用", required = true)
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @ApiModelProperty(value = "库存不能为空", required = true)
    @RangeInt(max = 9999, message = "最大库存9999")
    private Integer stock;

    @ApiModelProperty("划线价")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价", required = true)
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

}
