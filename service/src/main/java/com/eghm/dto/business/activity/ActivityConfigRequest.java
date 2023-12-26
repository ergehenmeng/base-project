package com.eghm.dto.business.activity;

import com.eghm.dto.ext.DateComparator;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityConfigRequest extends DateComparator {

    @ApiModelProperty(value = "活动名称", required = true)
    @Size(min = 2, max = 20, message = "活动名称长度2~20位")
    @NotBlank(message = "活动名称不能为空")
    @WordChecker(message = "活动名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "开始日期yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty(value = "周期 1:星期一 2:星期二 ... 7:星期日", required = true)
    @NotEmpty(message = "请选择周期")
    public List<Integer> week;

    @ApiModelProperty(value = "开始时间HH:mm", required = true)
    @NotNull(message = "开始时间不能为空")
    private String startTime;

    @ApiModelProperty(value = "结束时间HH:mm", required = true)
    @NotNull(message = "结束时间不能为空")
    private String endTime;

    @ApiModelProperty(value = "活动地点", required = true)
    @NotNull(message = "活动地点不能为空")
    @WordChecker(message = "活动地点存在敏感词")
    private String address;

    @ApiModelProperty(value = "活动封面图片", required = true)
    @NotNull(message = "活动封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "活动详细介绍", required = true)
    @NotNull(message = "活动详细介绍不能为空")
    @WordChecker(message = "活动详细介绍存在敏感词")
    private String introduce;

    @ApiModelProperty("活动关联的景区id")
    private Long scenicId;
}
