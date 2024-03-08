package com.eghm.dto.business.item;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.DeliveryType;
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
public class ItemCouponQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "交付方式 0:无需发货 1:快递包邮 2:自提")
    private DeliveryType deliveryType;

    @ApiModelProperty("排序规则 0: 默认排序 1: 按价格排序 2: 按销售量排序 3: 评分排序")
    private Integer sortBy;

    @ApiModelProperty(value = "优惠券id", required = true)
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

    @ApiModelProperty("标签id")
    private String tagId;

    @ApiModelProperty(value = "店铺id", hidden = true)
    @Assign
    private Long storeId;

    @ApiModelProperty(value = "使用范围 1:店铺券 2:商品券", hidden = true)
    @Assign
    private Integer useScope;
}
