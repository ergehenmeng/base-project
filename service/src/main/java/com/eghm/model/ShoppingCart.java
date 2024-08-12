package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-23
 */
@Data
@TableName("shopping_cart")
@EqualsAndHashCode(callSuper = true)
public class ShoppingCart extends BaseEntity {

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty("商户id(冗余)")
    private Long merchantId;

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

    @ApiModelProperty(value = "创建日期")
    private LocalDate createDate;

    @ApiModelProperty("创建月份")
    private String createMonth;

}
