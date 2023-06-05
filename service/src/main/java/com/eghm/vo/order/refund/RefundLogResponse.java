package com.eghm.vo.order.refund;

import com.eghm.enums.ref.AuditState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2023/6/5
 */
@Data
public class RefundLogResponse {

    @ApiModelProperty("退款记录id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "退款数量")
    private Integer num;

    @ApiModelProperty(value = "申请退款金额")
    private Integer applyAmount;

    @ApiModelProperty(value = "退款原因")
    private String reason;

    @ApiModelProperty(value = "退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款")
    private Integer state;

    @ApiModelProperty(value = "审核状态 0:待审核 1:审核通过 2:审核拒绝 3:取消审核")
    private AuditState auditState;

    @ApiModelProperty(value = "退款申请时间")
    private LocalDateTime applyTime;
}
