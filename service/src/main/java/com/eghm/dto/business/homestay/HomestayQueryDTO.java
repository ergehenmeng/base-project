package com.eghm.dto.business.homestay;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayQueryDTO extends DatePagingComparator {

    @Schema(description = "经度")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "按距离排序 1:正序 2:倒序")
    private Integer sortByDistance;

    @Schema(description = "按价格排序 1:正序 2:倒序")
    private Integer sortByPrice;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 0:其他(空查询全部)")
    @OptionInt(value = {0, 3, 4, 5}, message = "星级参数非法", required = false)
    private Integer level;

    @Schema(description = "开始日期(含)", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "入住日期不能为空")
    private LocalDate startDate;

    @Schema(description = "截止日期(不含)", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "离店日期不能为空")
    private LocalDate endDate;

    @Schema(description = "入住天数", hidden = true)
    @Assign
    private Long stayNum;
}
