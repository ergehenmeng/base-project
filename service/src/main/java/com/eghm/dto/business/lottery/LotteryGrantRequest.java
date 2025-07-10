
package com.eghm.dto.business.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull(message = "请填写备注")
    @Size(max = 200, message = "备注最大200字符")
    private String remark;
}
