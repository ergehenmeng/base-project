package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 线下退款记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-14
 */
@Data
@TableName("offline_refund_log")
@EqualsAndHashCode(callSuper = true)
public class OfflineRefundLog extends BaseEntity {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "退款金额")
    private Integer refundAmount;

    @Schema(description = "线下退款关联信息(json)")
    private String note;

    @Schema(description = "退款凭证(转账记录)")
    private String certificate;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "用户id")
    private Long userId;

}
