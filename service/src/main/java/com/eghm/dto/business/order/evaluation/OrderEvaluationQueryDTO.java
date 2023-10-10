package com.eghm.dto.business.order.evaluation;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderEvaluationQueryDTO extends PagingQuery {

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("1:最新 2: 好评 3: 中评 4:差评 5:有图")
    @OptionInt(value = {0, 1, 2, 3, 4, 5})
    private Integer queryType;
}
