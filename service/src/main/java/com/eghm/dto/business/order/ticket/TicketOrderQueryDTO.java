package com.eghm.dto.business.order.ticket;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TicketOrderQueryDTO extends PagingQuery {

    /**
     * TODO 定义门票状态值
     */
    @ApiModelProperty("门票订单状态")
    private Integer orderState;

    @ApiModelProperty("用户id")
    @Assign
    private Long memberId;
}
