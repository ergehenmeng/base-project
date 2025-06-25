
package com.eghm.dto.business.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
public class LotteryGrantRequest {

    @ApiModelProperty(value = "中奖id")
    @NotNull(message = "请选择要发放的记录")
    private Long id;

    @ApiModelProperty(value = "备注")
    private String remark;
}
