package com.eghm.dto.business.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2023/3/27
 */
@Data
public class LotteryRatioConfigRequest {

    @ApiModelProperty(value = "位置id", required = true)
    @NotBlank(message = "位置id不能为空")
    private Long id;

    @ApiModelProperty(value = "中奖比例", required = true)
    @NotNull(message = "中奖概率不能为空")
    private BigDecimal ratio;

}
