package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Data
public class RedeemScope {

    @ApiModelProperty("兑换码配置id")
    private Long redeemCodeId;

    @ApiModelProperty("商品ids")
    private List<Long> productIds;
}
