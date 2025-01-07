package com.eghm.vo.business.order.refund;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.AuditState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2023/6/5
 */
@Data
public class RefundLogResponse {

    @Schema(description = "退款记录id")
    private Long id;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "退款数量")
    private Integer num;

    @Schema(description = "申请退款金额(含快递费)")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer refundAmount;

    @Schema(description = "退款快递费")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer expressFee;

    @Schema(description = "退款原因")
    private String reason;

    @Schema(description = "退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款")
    private Integer state;

    @Schema(description = "审核状态 0:待审核 1:审核通过 2:审核拒绝 3:取消审核")
    private AuditState auditState;

    @Schema(description = "退款申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    @Schema(description = "退款审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;
}
