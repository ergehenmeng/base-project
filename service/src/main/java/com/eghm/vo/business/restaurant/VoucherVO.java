package com.eghm.vo.business.restaurant;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/10/24
 */
@Data
public class VoucherVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @Schema(description = "剩余库存")
    private Integer stock;

    @Schema(description = "销售数量")
    private Integer totalNum;
}
