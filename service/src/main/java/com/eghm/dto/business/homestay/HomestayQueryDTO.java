package com.eghm.dto.business.homestay;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayQueryDTO extends DatePagingComparator {

    @ApiModelProperty("经度")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty("纬度")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @ApiModelProperty("按距离排序 1:正序 2:倒序")
    private Integer sortByDistance;

    @ApiModelProperty("按价格排序 1:正序 2:倒序")
    private Integer sortByPrice;

    @ApiModelProperty("星级 5:五星级 4:四星级 3:三星级 0: 其他, 空查询全部")
    @OptionInt(value = {0, 3, 4, 5}, message = "星级参数非法", required = false)
    private Integer level;

    @ApiModelProperty("开始日期(含)")
    @sinceTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "入住日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty("截止日期(不含)")
    @sinceTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "离店日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty(value = "入住天数", hidden = true)
    @Assign
    private Long stayNum;
}
