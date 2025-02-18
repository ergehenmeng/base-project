package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.AuditState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/10
 */

@Data
public class ItemRefundResponse {

    @ApiModelProperty("退款id")
    private Long id;

    @ApiModelProperty(value = "申请退款金额(含快递费)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer applyAmount;

    @ApiModelProperty("退款快递费")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer expressFee;

    @ApiModelProperty(value = "实际退款金额(总费用)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer refundAmount;

    @ApiModelProperty(value = "退款原因")
    private String reason;

    @ApiModelProperty(value = "退款审核状态 0:待审核 1:审核通过 2:审核拒绝 3:取消审核")
    private AuditState auditState;

    @ApiModelProperty(value = "退款申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    @ApiModelProperty("审批人id")
    private String auditName;

    @ApiModelProperty(value = "退款审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "审批意见或建议")
    private String auditRemark;

    @ApiModelProperty(value = "物流公司(退货退款)")
    private String expressCode;

    @ApiModelProperty(value = "物流单号(退货退款)")
    private String expressNo;

    @ApiModelProperty("格式化后的物流节点信息(默认倒序)")
    private List<ExpressVO> expressList;
}
