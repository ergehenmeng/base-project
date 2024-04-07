package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.ChangeType;
import com.eghm.enums.ref.FreezeState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 商户资金冻结记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("account_freeze_log")
public class AccountFreezeLog extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "状态 1:冻结中 2:已解冻")
    private FreezeState state;

    @ApiModelProperty(value = "冻结金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @ApiModelProperty(value = "状态变更原因 1:支付冻结 2:退款解冻 3:订单完成解冻")
    private ChangeType changeType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "解冻时间")
    private LocalDateTime unfreezeTime;

}
