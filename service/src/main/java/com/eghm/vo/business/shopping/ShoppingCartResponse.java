package com.eghm.vo.business.shopping;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */

@Data
public class ShoppingCartResponse {

    @Schema(description = "商品id")
    private Long itemId;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "商品图片")
    private String coverUrl;

    @Schema(description = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @Schema(description = "最高价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer maxPrice;

    @Schema(description = "是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @Schema(description = "购物车商品数量")
    private Integer quantity;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "销售数量(所有规格销售总量)")
    private Integer saleNum;
}
