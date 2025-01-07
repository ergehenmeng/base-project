package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "商户id(冗余)")
    private Long merchantId;

    @Schema(description = "商铺id")
    private Long storeId;

    @Schema(description = "商品id")
    private Long itemId;

    @Schema(description = "商品规格id")
    private Long skuId;

    @Schema(description = "商品售价(冗余)")
    private Integer salePrice;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "创建日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;

}
