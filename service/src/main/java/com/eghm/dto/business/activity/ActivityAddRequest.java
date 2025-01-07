package com.eghm.dto.business.activity;

import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
public class ActivityAddRequest {

    @Schema(description = "活动名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "活动名称长度2~20位")
    @NotBlank(message = "活动名称不能为空")
    @WordChecker(message = "活动名称存在敏感词")
    private String title;

    @Schema(description = "活动日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "活动日期不能为空")
    private LocalDate nowDate;

    @Schema(description = "活动时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "活动时间不能为空")
    @Size(max = 20, message = "活动时间最多20字符")
    private String activityTime;

    @Schema(description = "活动地点", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "活动地点不能为空")
    @WordChecker(message = "活动地点存在敏感词")
    @Size(max = 50, message = "活动地点最多50字符")
    private String address;

    @Schema(description = "活动封面图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "活动封面图片不能为空")
    private String coverUrl;

    @Schema(description = "活动详细介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "活动详细介绍不能为空")
    @WordChecker(message = "活动详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

    @Schema(description = "活动关联的景区id")
    private Long scenicId;

    @Schema(description = "是否支持评论 true:支持 false:不支持")
    @NotNull(message = "是否支持评论不能为空")
    private Boolean commentSupport;
}
