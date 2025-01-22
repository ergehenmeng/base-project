package com.eghm.dto.business.order.refund;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.enums.event.IEvent;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/10/12
 */

@Data
public class PlatformRefundRequest {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "申请退款金额", required = true)
    @RangeInt(min = 1, max = 5000000, message = "退款金额应小于50000元")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer refundAmount;

    @ApiModelProperty(value = "退款数量(非实名制时该字段必填)")
    private Integer num;

    @ApiModelProperty(value = "退款原因", required = true)
    private String reason;

    @ApiModelProperty("退款游客id(实名制时该字段必填)")
    private List<Long> visitorIds;

    @Assign
    @ApiModelProperty(value = "触发退款类型", hidden = true)
    private IEvent event;
}
