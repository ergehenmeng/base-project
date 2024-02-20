package com.eghm.dto.business.withdraw.score;

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
public class ScoreWithdrawApplyDTO {

    @ApiModelProperty("商户id")
    @Assign
    private Long merchantId;

    @ApiModelProperty("提现金额")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @Min(value = 1000, message = "最低提现积分1000起")
    private Integer amount;
}
