package com.eghm.dto.business.activity;

import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
public class ActivityAddRequest {

    @ApiModelProperty(value = "活动名称", required = true)
    @Size(min = 2, max = 20, message = "活动名称长度2~20位")
    @NotBlank(message = "活动名称不能为空")
    @WordChecker(message = "活动名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "活动日期", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "活动日期不能为空")
    private LocalDate nowDate;

    @ApiModelProperty(value = "活动时间", required = true)
    @NotBlank(message = "活动时间不能为空")
    @Size(max = 20, message = "活动时间最多20字符")
    private String activityTime;

    @ApiModelProperty(value = "活动地点", required = true)
    @NotNull(message = "活动地点不能为空")
    @WordChecker(message = "活动地点存在敏感词")
    @Size(max = 50, message = "活动地点最多50字符")
    private String address;

    @ApiModelProperty(value = "活动封面图片", required = true)
    @NotNull(message = "活动封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "活动详细介绍", required = true)
    @NotNull(message = "活动详细介绍不能为空")
    @WordChecker(message = "活动详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

    @ApiModelProperty("活动关联的景区id")
    private Long scenicId;
}
