package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.AuditState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/10
 */

@Data
public class ItemRefundResponse {

    @Schema(description = "退款id")
    private Long id;

    @Schema(description = "申请退款金额(含快递费)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer applyAmount;

    @Schema(description = "退款快递费")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer expressFee;

    @Schema(description = "实际退款金额(总费用)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer refundAmount;

    @Schema(description = "退款原因")
    private String reason;

    @Schema(description = "退款审核状态 0:待审核 1:审核通过 2:审核拒绝 3:取消审核")
    private AuditState auditState;

    @Schema(description = "退款申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;

    @Schema(description = "审批人id")
    private String auditName;

    @Schema(description = "退款审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    @Schema(description = "审批意见或建议")
    private String auditRemark;

    @Schema(description = "物流公司(退货退款)")
    private String expressCode;

    @Schema(description = "物流单号(退货退款)")
    private String expressNo;

    @Schema(description = "格式化后的物流节点信息(默认倒序)")
    private List<ExpressVO> expressList;
}
