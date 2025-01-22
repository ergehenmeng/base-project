package com.eghm.dto.business.coupon.product;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.DeliveryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemCouponQueryDTO extends PagingQuery {

    @Schema(description = "交付方式 1:快递包邮 2:自提")
    private DeliveryType deliveryType;

    @Schema(description = "排序规则 0:默认排序 1:按价格排序 2:按销售量排序 3:评分排序")
    private Integer sortBy;

    @Schema(description = "优惠券id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

    @Schema(description = "标签id")
    private String tagId;

    @Schema(description = "店铺id", hidden = true)
    @Assign
    private Long storeId;

    @Schema(description = "使用范围 1:店铺券 2:商品券", hidden = true)
    @Assign
    private Integer useScope;
}
