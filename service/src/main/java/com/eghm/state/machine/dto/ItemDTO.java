package com.eghm.state.machine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/9/4
 */

@Data
public class ItemDTO {

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "店铺id")
    @NotNull(message = "店铺不能为空")
    private Long storeId;

    @Schema(description = "商品sku信息")
    @NotEmpty(message = "请选择对应的商品")
    private List<SkuDTO> skuList;

    @Schema(description = "积分")
    @Max(value = 100000, message = "积分不能超过100000")
    private Integer scoreAmount;

    @Schema(description = "备注")
    private String remark;

}
