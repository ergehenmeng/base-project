package com.eghm.dto.business.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 殿小二
 * @date 2023/3/27
 */
@Data
public class LotteryEditRatioRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "中奖配置信息", required = true)
    @NotEmpty(message = "中奖配置不能为空")
    @Size(min = 8, max = 8, message = "中奖配置应为8条")
    private List<LotteryRatioConfigRequest> ratioList;
    
}
