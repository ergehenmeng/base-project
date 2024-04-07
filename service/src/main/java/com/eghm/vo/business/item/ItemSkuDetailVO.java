package com.eghm.vo.business.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemSkuDetailVO {

    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty("多规格信息")
    private List<ItemSpecVO> specList;

    @ApiModelProperty("规格sku信息(单规格时只有一条)")
    private List<ItemSkuVO> skuList;
}
