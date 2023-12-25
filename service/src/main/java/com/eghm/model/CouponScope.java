package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券商品关联表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-13
 */
@Data
@NoArgsConstructor
@TableName("coupon_scope")
public class CouponScope implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "优惠券配置id")
    private Long couponConfigId;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime createTime;

    public CouponScope(Long couponConfigId, ProductType productType, Long productId) {
        this.couponConfigId = couponConfigId;
        this.productType = productType;
        this.productId = productId;
    }
}
