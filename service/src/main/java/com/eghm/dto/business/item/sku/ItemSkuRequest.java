package com.eghm.dto.business.item.sku;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2023/3/6
 */
@Data
public class ItemSkuRequest {

    @Schema(description = "id(编辑时不能为空)")
    private Long id;

    @Schema(description = "一级规格名(单规格为空)")
    @WordChecker(message = "一级规格存在敏感词")
    private String primarySpecValue;

    @Schema(description = "二级规格名(单规格为空)")
    @WordChecker(message = "二级规格存在敏感词")
    private String secondSpecValue;

    @Schema(description = "成本价")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer costPrice;

    @Schema(description = "划线价")
    @NotNull(message = "划线价不能为空")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer linePrice;

    @Schema(description = "销售价格")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "销售价不能为空不能为空")
    private Integer salePrice;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "库存")
    @RangeInt(max = 9999, message = "库存数应为0~9999")
    private Integer stock;

    @Schema(description = "虚拟销量")
    @RangeInt(max = 9999, message = "虚拟销量应为0~9999")
    private Integer virtualNum;

    @Schema(description = "sku图片(优先级最高)")
    private String skuPic;
}
