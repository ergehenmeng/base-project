package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/10
 */

@Data
public class ItemSkuStockResponse {

    @Schema(description = "sku_id")
    private Long id;

    @Schema(description = "一级规格名")
    private String primarySpecValue;

    @Schema(description = "二级规格名")
    private String secondSpecValue;

    @Schema(description = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @Schema(description = "剩余库存")
    private Integer stock;

    @Schema(description = "销售量")
    private Integer saleNum;

}
