package com.eghm.dto.business.line;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/27
 */
@Data
public class LineDayConfigRequest {

    @Schema(description = "行程排序(第几天)", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, message = "行程排序不合法")
    private Integer routeIndex;

    @Schema(description = "出发地点", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "出发地点不能为空")
    private String startPoint;

    @Schema(description = "结束地点", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "结束地点不能为空")
    private String endPoint;

    @Schema(description = "交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2, 3, 4, 5}, message = "交通方式不合法")
    private Integer trafficType;

    @Schema(description = "包含就餐 1:早餐 2:午餐 4:晚餐", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> repastList;

    @Schema(description = "详细描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细信息不能为空")
    @WordChecker(message = "详细信息存在敏感词")
    @Expose(serialize = false)
    private String depict;
}
