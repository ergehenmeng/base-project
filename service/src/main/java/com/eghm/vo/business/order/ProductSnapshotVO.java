package com.eghm.vo.business.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class ProductSnapshotVO {

    @ApiModelProperty("订单号")
    private Integer orderNo;
    
    @ApiModelProperty(value = "商品")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productTitle;

    @ApiModelProperty(value = "商品图片")
    private String productCover;
}
