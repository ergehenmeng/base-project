package com.eghm.vo.business.homestay.room.config;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @date 2022/7/7
 */
@Data
@NoArgsConstructor
public class RoomConfigVO {

    @ApiModelProperty("某一天的房态id")
    private Long id;

    @ApiModelProperty("状态 false:不可预定 true:可预定")
    private Boolean state;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate configDate;

    @ApiModelProperty("划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty("销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty("库存数")
    private Integer stock;

    public RoomConfigVO(Boolean state, LocalDate configDate) {
        this.state = state;
        this.configDate = configDate;
    }
}
