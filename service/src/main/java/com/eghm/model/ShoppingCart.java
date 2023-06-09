package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shopping_cart")
@ApiModel(value="ShoppingCart对象", description="购物车表")
public class ShoppingCart extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty("商铺id")
    private Long storeId;

    @ApiModelProperty(value = "商品id")
    private Long itemId;

    @ApiModelProperty(value = "商品规格id")
    private Long skuId;

    @ApiModelProperty(value = "商品售价(冗余)")
    private Integer salePrice;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

}
