package com.eghm.dto.business.item.express;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/23
 */
@Data
public class ItemCalcDTO {

    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择商品")
    private Long itemId;

    @Schema(description = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择商品规格")
    private Long skuId;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "购买数量必须大于等于1")
    @NotNull(message = "商品数量不能为空")
    private Integer num;

    @Assign
    @Schema(description = "物流模板id", hidden = true)
    private Long expressId;

    @Assign
    @Schema(description = "快递计费方式 1:计件 2:计费", hidden = true)
    private Integer chargeMode;

    @Assign
    @Schema(description = "重量", hidden = true)
    private BigDecimal weight;

    /**
     * 计算好每个商品的快递费后会填充到该字段上
     */
    @Assign
    @Schema(description = "快递费", hidden = true)
    private Integer expressFee = 0;
}
