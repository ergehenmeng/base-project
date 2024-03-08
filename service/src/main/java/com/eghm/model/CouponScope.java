package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.dto.ext.ProductScope;
import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class CouponScope {

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime createTime;

    public CouponScope(Long couponId, ProductScope scope) {
        this.couponId = couponId;
        this.productType = scope.getProductType();
        this.productId = scope.getProductId();
    }
}
