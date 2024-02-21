package com.eghm.dto.business.lottery;

import com.eghm.enums.ref.PrizeType;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @since 2023/3/27
 */
@Data
public class LotteryPrizeRequest {

    @ApiModelProperty(value = "奖品名称", required = true)
    @NotBlank(message = "奖品名称不能为空")
    @Length(min = 2, max = 10, message = "奖品名称长度应为2~10字符")
    @WordChecker(message = "奖品名称存在敏感词")
    private String prizeName;

    @ApiModelProperty(value = "奖品类型", required = true)
    @NotNull(message = "奖品类型不能为空")
    private PrizeType prizeType;

    @ApiModelProperty(value = "奖品总数量", required = true)
    @NotNull(message = "奖品数量不能为空")
    private Integer totalNum;

    @ApiModelProperty(value = "奖品图片", required = true)
    @NotBlank(message = "奖品图片不能为空")
    private String coverUrl;

}
