package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/12/30
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ItemVO {

    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @Schema(description = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "商品状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;
}
