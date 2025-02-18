package com.eghm.vo.business.account;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/6/20
 */

@Data
public class ScoreRechargeResponse {

    @ApiModelProperty(value = "可用余额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @ApiModelProperty(value = "可用积分")
    private Integer scoreAmount;

    @ApiModelProperty(value = "最低充值金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minRecharge;
}
