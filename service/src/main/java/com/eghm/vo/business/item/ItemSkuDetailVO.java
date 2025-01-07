package com.eghm.vo.business.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemSkuDetailVO {

    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "多规格信息")
    private List<ItemSpecVO> specList;

    @Schema(description = "规格sku信息(单规格时只有一条)")
    private List<ItemSkuVO> skuList;
}
