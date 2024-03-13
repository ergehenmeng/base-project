package com.eghm.dto.business.order.evaluation;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderEvaluationQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "商品id")
    @NotNull(message = "商品id不能为空")
    private Long productId;

    @ApiModelProperty(value = "1:最新 2: 好评 3: 中评 4:差评 5:有图", hidden = true)
    @OptionInt(value = {0, 1, 2, 3, 4, 5})
    private Integer queryType = 1;
}
