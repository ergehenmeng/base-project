package com.eghm.dto.business.line;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2022/8/27
 */
@Data
public class LineDayConfigRequest {

    @ApiModelProperty(value = "行程排序(第几天)", required = true)
    @OptionInt(value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, message = "行程排序不合法")
    private Integer routeIndex;

    @ApiModelProperty(value = "出发地点", required = true)
    @NotBlank(message = "出发地点不能为空")
    private String startPoint;

    @ApiModelProperty(value = "结束地点", required = true)
    @NotBlank(message = "结束地点不能为空")
    private String endPoint;

    @ApiModelProperty(value = "交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他", required = true)
    @OptionInt(value = {1, 2, 3, 4, 5}, message = "交通方式不合法")
    private Integer trafficType;

    @ApiModelProperty(value = "包含就餐 1:早餐 2:午餐 4:晚餐", required = true)
    @Max(value = 7, message = "就餐信息不合法")
    private Integer repast;

    @ApiModelProperty(value = "详细描述信息", required = true)
    @NotBlank(message = "详细信息不能为空")
    @WordChecker(message = "详细信息存在敏感词")
    private String depict;
}
