package com.eghm.vo.business.shopping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */

@Data
public class ShoppingCartVO {

    @Schema(description = "店铺名称")
    private String storeTitle;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "购物车商品列表")
    private List<ShoppingCartItemVO> itemList;
}
