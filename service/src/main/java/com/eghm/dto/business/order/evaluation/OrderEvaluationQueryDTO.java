package com.eghm.dto.business.order.evaluation;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderEvaluationQueryDTO extends PagingQuery {

    @Schema(description = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品id不能为空")
    private Long productId;

    @Schema(description = "1:最新 2:好评 3: 中评 4:差评 5:有图", hidden = true)
    @OptionInt(value = {0, 1, 2, 3, 4, 5})
    private Integer queryType;
}
