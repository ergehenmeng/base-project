package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class BaseSkuResponse {

    @Schema(description = "商品id(冗余)")
    private Long itemId;

    @Schema(description = "商品名称(冗余)")
    private String title;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "商品规格数量(冗余)")
    private Integer skuSize;

    @Schema(description = "一级规格名(单规格为空)")
    @JsonIgnore
    private String primarySpecValue;

    @Schema(description = "二级规格名(单规格为空)")
    @JsonIgnore
    private String secondSpecValue;

    @Schema(description = "规格名(单规格为空)")
    private String specValue;

    @Schema(description = "销售价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "规格图片(可能为空)")
    @JsonIgnore
    private String specPic;

    @Schema(description = "sku图片(可能为空)")
    private String skuPic;
}
