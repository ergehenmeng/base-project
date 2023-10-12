package com.eghm.dto.business.order.ticket;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.OrderState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wyb
 * @since 2023/6/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TicketOrderQueryRequest extends PagingQuery {

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("联系人手机号")
    private String mobile;

    @ApiModelProperty("订单状态")
    private OrderState state;
}
