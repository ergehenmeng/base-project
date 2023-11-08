package com.eghm.dto.business.item.express;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/23
 */
@Data
public class ItemCalcDTO {

    @ApiModelProperty("商品ID")
    @NotNull(message = "请选择商品")
    private Long itemId;

    @ApiModelProperty("skuId")
    @NotNull(message = "请选择商品规格")
    private Long skuId;

    @ApiModelProperty("数量")
    @Min(value = 1, message = "购买数量必须大于等于1")
    @NotNull(message = "商品数量不能为空")
    private Integer num;

    @Assign
    @ApiModelProperty(value = "物流模板id", hidden = true)
    private Long expressId;

    @Assign
    @ApiModelProperty(value = "快递计费方式 1:计件 2:计费", hidden = true)
    private Integer chargeMode;

    @Assign
    @ApiModelProperty(value = "重量", hidden = true)
    private BigDecimal weight;

    /**
     * 计算好每个商品的快递费后会填充到该字段上
     */
    @Assign
    @ApiModelProperty(value = "快递费", hidden = true)
    private Integer expressFee;
}
