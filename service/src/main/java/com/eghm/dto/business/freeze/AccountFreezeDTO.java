package com.eghm.dto.business.freeze;

import com.eghm.enums.ChangeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */
@Data
public class AccountFreezeDTO {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "冻结金额")
    private Integer amount;

    @ApiModelProperty(value = "状态变更原因 1:支付冻结 2:退款解冻 3:订单完成解冻")
    private ChangeType changeType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
