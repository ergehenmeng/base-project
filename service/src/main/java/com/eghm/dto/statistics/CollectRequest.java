package com.eghm.dto.statistics;

import com.eghm.annotation.DateFormatter;
import com.eghm.enums.ref.CollectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class CollectRequest {

    @ApiModelProperty("开始日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty("截止日期 yyyy-MM-dd")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty("收藏类型")
    private CollectType collectType;
}
