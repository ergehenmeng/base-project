package com.eghm.dto.business.account.score;

import com.eghm.enums.ref.ChargeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/15
 */

@Data
public class ScoreAccountDTO {

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("变动积分")
    private Integer amount;

    @ApiModelProperty("账户类型")
    private ChargeType chargeType;

    @ApiModelProperty("备注信息")
    private String remark;

}
