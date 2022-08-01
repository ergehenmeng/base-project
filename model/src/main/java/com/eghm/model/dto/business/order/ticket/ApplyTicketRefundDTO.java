package com.eghm.model.dto.business.order.ticket;

import com.eghm.model.annotation.Sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/30
 */

@Data
public class ApplyTicketRefundDTO {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("退款数量")
    private Integer num;

    @ApiModelProperty("退款游客id")
    private List<Long> visitorIds;

    @Sign
    @ApiModelProperty("用户id")
    private Long userId;
}
