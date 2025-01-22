package com.eghm.dto.business.lottery;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @since 2023/3/27
 */
@Data
public class LotteryConfigRequest {

    @Schema(description = "奖品下标0-7", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择奖品")
    @RangeInt(max = 7, message = "奖品位置应在0~7之间")
    private Integer prizeIndex;

    @Schema(description = "中奖位置 1-8", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "中奖位置不能为空")
    @RangeInt(min = 1, max = 8, message = "中奖位置应为1~8之间")
    private Integer location;

    @Schema(description = "中奖比例", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "中奖概率不能为空")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer weight;

}
