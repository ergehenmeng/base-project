package com.eghm.vo.business.freeze;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.ChangeType;
import com.eghm.enums.ref.FreezeState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */

@Data
public class AccountFreezeLogResponse {

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "冻结金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @ApiModelProperty(value = "状态 1:冻结中 2:已解冻")
    private FreezeState state;

    @ApiModelProperty(value = "变更类型 1:支付冻结, 2:退款解冻 3:订单完成解冻")
    private ChangeType changeType;

    @ApiModelProperty(value = "冻结时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "解冻时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime unfreezeTime;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
