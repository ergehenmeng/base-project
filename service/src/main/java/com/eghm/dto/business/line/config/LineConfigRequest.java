package com.eghm.dto.business.line.config;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.dto.ext.DateComparator;
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
public class LineConfigRequest extends DateComparator {

    @ApiModelProperty("周期")
    @NotEmpty(message = "请选择周期")
    private List<Integer> week;

    @ApiModelProperty("线路ID")
    @NotNull(message = "线路id不能为空")
    private Long lineId;

    @ApiModelProperty("开始日期 yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("截止日期 yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty("状态 0:不可用 1:可用")
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
