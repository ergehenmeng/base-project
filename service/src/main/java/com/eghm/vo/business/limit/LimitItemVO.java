package com.eghm.vo.business.limit;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitItemVO {

    @Schema(description = "商品ID")
    private Long itemId;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "最低价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @Schema(description = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @Schema(description = "最大优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer maxDiscountAmount;

    @Schema(description = "是否进行中")
    private Boolean onSale;
}
