package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
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
    @Min(value = 1000, message = "最低提现1000积分起")
    @NotNull(message = "请输入提现积分数")
    private Integer amount;
}
