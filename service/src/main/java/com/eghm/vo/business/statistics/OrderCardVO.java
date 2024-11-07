package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCardVO {

    @ApiModelProperty("订单数量(含退款的订单)")
    private Integer orderNum = 0;

    @ApiModelProperty("支付金额(含退款的订单)")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount = 0;

    @ApiModelProperty("退款数量(包含部分退款)")
    private Integer refundNum = 0;

    @ApiModelProperty("退款金额(包含部分退款)")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer refundAmount = 0;

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum + RandomUtil.randomInt(1000);
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount + RandomUtil.randomInt(1000000);
    }

    public void setRefundNum(Integer refundNum) {
        this.refundNum = refundNum + RandomUtil.randomInt(1000);
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount + RandomUtil.randomInt(1000000);
    }
}
