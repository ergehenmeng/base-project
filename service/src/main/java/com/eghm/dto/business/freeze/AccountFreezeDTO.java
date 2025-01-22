package com.eghm.dto.business.freeze;

import com.eghm.enums.ChangeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */
@Data
public class AccountFreezeDTO {

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "冻结金额")
    private Integer amount;

    @Schema(description = "状态变更原因 1:支付冻结 2:退款解冻 3:订单完成解冻")
    private ChangeType changeType;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "备注信息")
    private String remark;
}
