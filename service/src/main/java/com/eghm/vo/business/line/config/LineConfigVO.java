package com.eghm.vo.business.line.config;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineConfigVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate configDate;

    @ApiModelProperty("是否可订 0:不可订 1:可定")
    private Integer state;

    @ApiModelProperty("划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @ApiModelProperty("销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @ApiModelProperty("库存数")
    private Integer stock;
}
