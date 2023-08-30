package com.eghm.dto.business.order.refund;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class TicketRefundApplyDTO {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "退款数量", required = true)
    @RangeInt(min = 1, max = 99, message = "退款数量应为1~99")
    private Integer num;

    @ApiModelProperty(value = "申请退款金额", required = true)
    @RangeInt(min = 1, max = 9999999, message = "退款金额不合法")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer applyAmount;

    @ApiModelProperty(value = "退款原因", required = true)
    private String reason;

    @ApiModelProperty("退款游客id")
    private List<Long> visitorIds;
}
