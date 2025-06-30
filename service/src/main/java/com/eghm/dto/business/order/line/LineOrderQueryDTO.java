package com.eghm.dto.business.order.line;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.OrderState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LineOrderQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "订单状态 0:待支付 2:待使用 7:订单完成 8:已关闭")
    private OrderState state;

    @ApiModelProperty("用户id")
    @Assign
    private Long memberId;
}
