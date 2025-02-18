package com.eghm.vo.business.statistics;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/2
 */

@Data
public class SaleStatisticsVO {

    @ApiModelProperty("销售额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品图片")
    private String productImg;

    @ApiModelProperty("商品名称")
    private String productName;
}
