package com.eghm.model.vo.business.line.config;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class LineConfigVO {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("是否设置了价格 true:是 false:否")
    private Boolean hasSet;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate configDate;

    @ApiModelProperty("是否可订 0:不可订 1:可定")
    private Integer state;

    @ApiModelProperty("划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty("销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty("库存数")
    private Integer stock;
}
