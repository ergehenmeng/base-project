package com.eghm.dto.business.coupon.config;

import com.eghm.enums.ProductType;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 优惠券编辑时,部分字段不支持修改
 *
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Data
public class CouponEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "库存(发放数量)", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 9999, message = "库存应为0~9999")
    private Integer stock;

    @Schema(description = "单人领取限制", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 99, message = "单人领取限制1~99")
    private Integer maxLimit;

    @Schema(description = "使用说明", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "使用说明最大100字符")
    @WordChecker(message = "使用说明存在敏感词")
    private String instruction;

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "关联的商品列表")
    private List<Long> productIds;
}
