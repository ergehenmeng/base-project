package com.eghm.vo.business.homestay.room.config;

import com.eghm.convertor.CentToYuanOmitEncoder;
import com.eghm.vo.business.BaseConfigResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022/6/29 22:11
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RoomConfigResponse extends BaseConfigResponse {

    @ApiModelProperty("是否可订 false:不可订 true:可定")
    private Boolean state;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate configDate;

    @ApiModelProperty("划线价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer linePrice;

    @ApiModelProperty("销售价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer salePrice;

    @ApiModelProperty("库存数")
    private Integer stock;

}
