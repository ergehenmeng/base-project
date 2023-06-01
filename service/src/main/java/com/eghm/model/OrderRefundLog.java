package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.AuditState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@TableName("order_refund_log")
@ApiModel(value="OrderRefundLog对象", description="订单退款记录表")
public class OrderRefundLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "退款人id")
    private Long userId;

    @ApiModelProperty(value = "退款流水号")
    private String outRefundNo;

    @ApiModelProperty(value = "退款数量")
    private Integer num;

    @ApiModelProperty("普通商品订单id(普通商品退款,该字段必填)")
    private Long itemOrderId;

    @ApiModelProperty(value = "申请退款金额")
    private Integer applyAmount;

    @ApiModelProperty(value = "实际退款金额")
    private Integer refundAmount;

    @ApiModelProperty(value = "退款原因")
    private String reason;

    @ApiModelProperty(value = "申请方式 1:仅退款 2:退货退款")
    private Integer applyType;

    @ApiModelProperty(value = "退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款")
    private Integer state;

    @ApiModelProperty(value = "审核状态 0:待审核 1:审核通过 2:审核拒绝 3:取消审核")
    private AuditState auditState;

    @ApiModelProperty(value = "退款审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "审批意见或建议")
    private String auditRemark;

    @ApiModelProperty(value = "退款申请时间")
    private LocalDateTime applyTime;

    @ApiModelProperty(value = "物流公司(退货退款)")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号(退货退款)")
    private String logisticsNo;

}
