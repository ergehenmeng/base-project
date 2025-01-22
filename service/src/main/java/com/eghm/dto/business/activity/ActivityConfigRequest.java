package com.eghm.dto.business.activity;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.dto.ext.AbstractDateComparator;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * 配置活动(批量添加活动)
 *
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityConfigRequest extends AbstractDateComparator {

    @ApiModelProperty(value = "周期 1:星期一 2:星期二 ... 7:星期日", required = true)
    @NotEmpty(message = "请选择周期")
    private List<Integer> week;

    @ApiModelProperty(value = "活动名称", required = true)
    @Size(min = 2, max = 20, message = "活动名称长度2~20位")
    @NotBlank(message = "活动名称不能为空")
    @WordChecker(message = "活动名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "开始日期yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    @ApiModelProperty(value = "活动时间", required = true)
    @NotBlank(message = "活动时间不能为空")
    @Size(max = 20, message = "活动时间最多20字符")
    private String activityTime;

    @ApiModelProperty(value = "活动地点", required = true)
    @NotBlank(message = "活动地点不能为空")
    @WordChecker(message = "活动地点存在敏感词")
    @Size(max = 50, message = "活动地点最多50字符")
    private String address;

    @ApiModelProperty(value = "活动封面图片", required = true)
    @NotBlank(message = "活动封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "活动详细介绍", required = true)
    @NotBlank(message = "活动详细介绍不能为空")
    @WordChecker(message = "活动详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

    @ApiModelProperty("活动关联的景区id")
    private Long scenicId;
}
