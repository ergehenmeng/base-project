package com.eghm.model.vo.business.line.config;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.eghm.model.vo.business.BaseConfigResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2022/8/30
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class LineConfigResponse extends BaseConfigResponse {

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
