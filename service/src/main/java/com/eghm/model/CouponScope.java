package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "添加时间")
    private LocalDateTime createTime;

    public CouponScope(Long couponId, ProductType productType, Long productId) {
        this.couponId = couponId;
        this.productType = productType;
        this.productId = productId;
    }
}
