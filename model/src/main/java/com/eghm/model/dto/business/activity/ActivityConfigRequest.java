package com.eghm.model.dto.business.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class ActivityConfigRequest {

    @ApiModelProperty(value = "活动名称")
    @Size(min = 2, max = 20, message = "活动名称2~20位")
    private String title;

    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty("周期 1:星期一 2:星期二 ... 7:星期日")
    @NotEmpty(message = "请选择周期")
    public List<Integer> week;

    @ApiModelProperty(value = "开始时间HH:mm")
    @NotNull(message = "开始时间不能为空")
    private String startTime;

    @ApiModelProperty(value = "结束时间HH:mm")
    @NotNull(message = "结束时间不能为空")
    private String endTime;

    @ApiModelProperty(value = "活动地点")
    @NotNull(message = "活动地点不能为空")
    private String address;

    @ApiModelProperty(value = "活动封面图片")
    @NotNull(message = "活动封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "活动详细介绍")
    @NotNull(message = "活动详细介绍不能为空")
    private String introduce;

}
