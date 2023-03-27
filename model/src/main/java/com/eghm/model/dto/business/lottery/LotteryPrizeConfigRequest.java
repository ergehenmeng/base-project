package com.eghm.model.dto.business.lottery;

import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 殿小二
 * @date 2023/3/27
 */
@Data
public class LotteryPrizeConfigRequest {
    
    @ApiModelProperty(value = "奖品位置0-7")
    @NotBlank(message = "请选择奖品")
    private Integer prizeIndex;
    
    @ApiModelProperty("奖品类型不能为空")
    private Integer prizeType;
    
    @ApiModelProperty(value = "中奖位置 1-8")
    @NotNull(message = "中奖位置不能为空")
    @RangeInt(min = 1, max = 8, message = "中奖位置应为1~8之间")
    private Integer location;
    
    @ApiModelProperty(value = "中奖比例")
    @NotNull(message = "中奖概率不能为空")
    private BigDecimal ratio;
    
}
