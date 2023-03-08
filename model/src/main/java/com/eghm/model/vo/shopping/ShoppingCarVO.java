package com.eghm.model.vo.shopping;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */

@Data
public class ShoppingCarVO {

    @ApiModelProperty("店铺名称")
    private String storeTitle;

    @ApiModelProperty("店铺id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long storeId;

    @ApiModelProperty("购物车商品列表")
    private List<ShoppingCartItemVO> productList;
}
