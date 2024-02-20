package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author 二哥很猛
 * @since 2024/2/20
 */

@Data
public class ScoreRechargeDTO {

    @ApiModelProperty("商户id")
    @Assign
    private Long merchantId;

    @ApiModelProperty("充值金额")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @Min(value = 1000, message = "最低充值金额10元")
    private Integer amount;
}
