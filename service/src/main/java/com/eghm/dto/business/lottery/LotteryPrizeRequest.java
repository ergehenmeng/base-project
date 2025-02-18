package com.eghm.dto.business.lottery;

import com.eghm.enums.PrizeType;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 殿小二
 * @since 2023/3/27
 */
@Data
public class LotteryPrizeRequest {

    @Schema(description = "奖品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖品名称不能为空")
    @Length(min = 1, max = 10, message = "奖品名称长度应为2~10字符")
    @WordChecker(message = "奖品名称存在敏感词")
    private String prizeName;

    @Schema(description = "奖品类型 0:谢谢参与 1:优惠券 2:积分", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "奖品类型不能为空")
    private PrizeType prizeType;

    @Schema(description = "关联商品ID")
    private Long relationId;

    @Schema(description = "单次中奖发放数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单次中奖数量不能为空")
    private Integer num;

    @Schema(description = "奖品总数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "奖品数量不能为空")
    private Integer totalNum;

    @Schema(description = "奖品图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖品图片不能为空")
    private String coverUrl;

}
