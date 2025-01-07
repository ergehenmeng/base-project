package com.eghm.dto.ext;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Data
public class RedeemScope {

    @Schema(description = "兑换码配置id")
    private Long redeemCodeId;

    @Schema(description = "商品ids")
    private List<Long> productIds;
}
