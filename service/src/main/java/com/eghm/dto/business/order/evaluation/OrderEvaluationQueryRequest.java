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
public class OrderEvaluationQueryRequest extends PagingQuery {

    @ApiModelProperty("评价状态 0:待审核 1:审核通过 2:审核拒绝")
    private Integer state;

    @ApiModelProperty("商品分类")
    private String productType;

    @ApiModelProperty("评价类型 0:好评 1:中评 2:差评")
    @OptionInt(value = {0, 1, 2}, required = false, message = "评价类型格式非法")
    private Integer queryType;

    @ApiModelProperty(value = "商品id")
    private Long productId;
}
