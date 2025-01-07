package com.eghm.vo.business.limit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class LimitItemResponse {

    @Schema(description = "商品id")
    private Long itemId;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "规格优惠配置json")
    @JsonIgnore
    private String skuValue;
}
