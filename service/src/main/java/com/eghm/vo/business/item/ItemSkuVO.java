package com.eghm.vo.business.item;

import com.eghm.convertor.BigDecimalOmitSerializer;
import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemSkuVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "规格id,多个逗号分隔")
    private String specId;

    @Schema(description = "一级规格名")
    private String primarySpecValue;

    @Schema(description = "二级规格名")
    private String secondSpecValue;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @Schema(description = "销售价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "拼团或限时购价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer discountPrice;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "销售量")
    private Integer saleNum;

    @Schema(description = "重量")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal weight;

    @Schema(description = "sku图片(优先级最高)")
    private String skuPic;
}
