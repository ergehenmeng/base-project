package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/2/20
 */

@Data
public class ScoreWithdrawApplyDTO {

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @ApiModelProperty(value = "提现金额", required = true)
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "请输入提现金额")
    private Integer amount;
}
