package com.eghm.model.dto.business.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @date 2023/3/27
 */
@Data
public class LotteryPrizeRequest {
    
    @ApiModelProperty(value = "奖品名称")
    @NotBlank(message = "奖品名称不能为空")
    @Length(min = 2, max = 10, message = "奖品名称长度应为2~10字符")
    private String prizeName;
    
    @ApiModelProperty(value = "奖品类型")
    @NotNull(message = "奖品类型不能为空")
    private Integer prizeType;
    
    @ApiModelProperty(value = "奖品总数量")
    @NotNull(message = "奖品数量不能为空")
    private Integer totalNum;
    
    @ApiModelProperty(value = "奖品图片")
    @NotBlank(message = "奖品图片不能为空")
    private String coverUrl;
    
}
