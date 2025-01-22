package com.eghm.vo.business.item;

import com.eghm.convertor.BigDecimalOmitSerializer;
import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2023/3/7
 */

@Data
public class ItemSkuResponse {

    @Schema(description = "id(编辑时不能为空)")
    private Long id;

    @Schema(description = "一级规格名")
    private String primarySpecValue;

    @Schema(description = "二级规格名(单规格为空)")
    private String secondSpecValue;

    @Schema(description = "一级规格值数量(方便前端设置单元格)")
    private String specIds;

    @Schema(description = "二级规格值数量(方便前端设置单元格)")
    private Integer secondSize;

    @Schema(description = "成本价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer costPrice;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @Schema(description = "销售价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "虚拟销量")
    private Integer virtualNum;

    @Schema(description = "sku图片(优先级最高)")
    private String skuPic;

    @Schema(description = "重量")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal weight;

}
