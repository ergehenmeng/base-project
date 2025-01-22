package com.eghm.dto.business.order.adjust;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.dto.ext.ActionRecord;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderAdjustRequest extends ActionRecord {

    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "商品订单id")
    @NotNull(message = "请选择要改价的商品")
    private Long id;

    @Schema(description = "新价格")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "请输入新价格")
    private Integer price;

}
