package com.eghm.vo.business.shopping;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */

@Data
public class ShoppingCartVO {

    @ApiModelProperty("店铺名称")
    private String storeTitle;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("购物车商品列表")
    private List<ShoppingCartItemVO> itemList;
}
