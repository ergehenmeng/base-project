package com.eghm.vo.business.shopping;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */

@Data
public class ShoppingCartResponse {

    @ApiModelProperty("商品id")
    private Long itemId;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("商品图片")
    private String coverUrl;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty(value = "最高价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer maxPrice;

    @ApiModelProperty("是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @ApiModelProperty("购物车商品数量")
    private Integer num;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2: 强制下架")
    private State state;

    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    private Integer saleNum;
}
