package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.AuditState;
import com.eghm.enums.RefundLogState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单退款记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-01
 */
@Data
@TableName("order_refund_log")
@EqualsAndHashCode(callSuper = true)
public class OrderRefundLog extends BaseEntity {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "退款人id")
    private Long memberId;

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "退款流水号")
    private String refundNo;

    @Schema(description = "退款数量")
    private Integer num;

    @Schema(description = "普通商品订单id(普通商品退款,该字段必填)")
    private Long itemOrderId;

    @Schema(description = "快递费用")
    private Integer expressFee;

    @Schema(description = "退款金额(含快递费)")
    private Integer refundAmount;

    @Schema(description = "退款积分")
    private Integer scoreAmount;

    @Schema(description = "退款原因")
    private String reason;

    @Schema(description = "申请方式 1:仅退款 2:退货退款")
    private Integer applyType;

    @Schema(description = "退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款 4:拒绝退款")
    private RefundLogState state;

    @Schema(description = "审核状态 (0:待审核 1:审核通过 2:审核拒绝 3:取消审核)")
    private AuditState auditState;

    @Schema(description = "审批人id")
    private Long auditUserId;

    @Schema(description = "退款审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    @Schema(description = "审批意见或建议")
    private String auditRemark;

    @Schema(description = "退款申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    @Schema(description = "物流公司(退货退款)")
    private String expressCode;

    @Schema(description = "物流单号(退货退款)")
    private String expressNo;

}
