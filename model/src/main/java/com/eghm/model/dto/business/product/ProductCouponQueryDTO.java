package com.eghm.model.dto.business.product;

import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCouponQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @ApiModelProperty("排序规则 1: 按价格排序 2: 按销售量排序 其他:默认推荐排序")
    private Integer sortBy;

    @ApiModelProperty("优惠券id")
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

    @ApiModelProperty(value = "店铺id", hidden = true)
    private Long storeId;

    @ApiModelProperty(value = "使用范围 1:店铺券 2:商品券", hidden = true)
    private Integer useScope;
}
