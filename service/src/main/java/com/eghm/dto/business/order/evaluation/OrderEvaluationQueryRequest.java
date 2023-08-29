package com.eghm.dto.business.order.evaluation;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.ProductType;
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
    private ProductType productType;

    @ApiModelProperty("分数")
    @OptionInt(value = {1, 2, 3, 4, 5}, required = false, message = "分数格式非法")
    private Integer score;
}
