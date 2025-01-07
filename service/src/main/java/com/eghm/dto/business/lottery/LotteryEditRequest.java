package com.eghm.dto.business.lottery;

import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 殿小二
 * @since 2023/3/27
 */
@Data
public class LotteryEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "活动名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "活动名称不能为空")
    @Size(min = 2, max = 20, message = "活动名称应为2~20字符")
    private String title;

    @Schema(description = "抽奖banner图", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "banner图不能为空")
    private String bannerUrl;

    @Schema(description = "开始时间 yyyy-MM-dd HH:mm", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "结束时间 yyyy-MM-dd HH:mm", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:m")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @Schema(description = "单日抽奖次数限制", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单日抽奖次数不能为空")
    @RangeInt(min = 1, max = 9999, message = "单日抽奖次数应为1~9999次")
    private Integer lotteryDay;

    @Schema(description = "总抽奖次数限制", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总抽奖次数不能为空")
    @RangeInt(min = 1, max = 9999, message = "总抽奖次数应为1~9999次")
    private Integer lotteryTotal;

    @Schema(description = "中奖次数限制", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "中奖次数不能为空")
    @RangeInt(min = 1, max = 9999, message = "中奖次数应为1~9999次")
    private Integer winNum;

    @Schema(description = "背景图", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择背景图")
    private String coverUrl;

    @Schema(description = "抽奖标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "抽奖标题不能为空")
    @Length(min = 2, max = 10, message = "抽奖标题长度应为2~10字符")
    @WordChecker(message = "抽奖标题存在敏感词")
    private String subTitle;

    @Schema(description = "抽奖规则", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "抽奖规则不能为空")
    @Length(min = 10, max = 1000, message = "抽奖规则长度为10~1000字符")
    @WordChecker(message = "抽奖规则存在敏感词")
    private String rule;

    @NotEmpty(message = "奖品列表不能为空")
    @Size(min = 1, max = 8, message = "奖品列表不能大于8个")
    @Schema(description = "奖品列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<LotteryPrizeRequest> prizeList;

    @Schema(description = "中奖配置信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "中奖配置不能为空")
    @Size(min = 8, max = 8, message = "中奖配置应为8条")
    private List<LotteryConfigRequest> configList;

    @Schema(description = "所属商户id", hidden = true)
    private Long merchantId;

}
