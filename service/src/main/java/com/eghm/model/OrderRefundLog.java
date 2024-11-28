package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.AuditState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "退款人id")
    private Long memberId;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty(value = "退款流水号")
    private String refundNo;

    @ApiModelProperty(value = "退款数量")
    private Integer num;

    @ApiModelProperty("普通商品订单id(普通商品退款,该字段必填)")
    private Long itemOrderId;

    @ApiModelProperty("快递费用")
    private Integer expressFee;

    @ApiModelProperty(value = "退款金额(含快递费)")
    private Integer refundAmount;

    @ApiModelProperty(value = "退款积分")
    private Integer scoreAmount;

    @ApiModelProperty(value = "退款原因")
    private String reason;

    @ApiModelProperty(value = "申请方式 1:仅退款 2:退货退款")
    private Integer applyType;

    @ApiModelProperty(value = "退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款 4:拒绝退款")
    private Integer state;

    @ApiModelProperty(value = "审核状态 (0:待审核 1:审核通过 2:审核拒绝 3:取消审核)")
    private AuditState auditState;

    @ApiModelProperty("审批人id")
    private Long auditUserId;

    @ApiModelProperty(value = "退款审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "审批意见或建议")
    private String auditRemark;

    @ApiModelProperty(value = "退款申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    @ApiModelProperty(value = "物流公司(退货退款)")
    private String expressCode;

    @ApiModelProperty(value = "物流单号(退货退款)")
    private String expressNo;

}
