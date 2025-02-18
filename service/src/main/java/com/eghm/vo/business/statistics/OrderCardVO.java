package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.eghm.convertor.CentToYuanSerializer;
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
    private Integer orderNum;

    @ApiModelProperty("支付金额(含退款的订单)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @ApiModelProperty("退款数量(包含部分退款)")
    private Integer refundNum;

    @ApiModelProperty("退款金额(包含部分退款)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer refundAmount;

    public void setOrderNum(Integer orderNum) {
        this.orderNum = (orderNum != null ? orderNum : 0) + RandomUtil.randomInt(1000);
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = (payAmount != null ? payAmount : 0) + RandomUtil.randomInt(1000000);
    }

    public void setRefundNum(Integer refundNum) {
        this.refundNum = (refundNum != null ? refundNum : 0) + RandomUtil.randomInt(1000);
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = (refundAmount != null ? refundAmount : 0) + RandomUtil.randomInt(1000000);
    }
}
