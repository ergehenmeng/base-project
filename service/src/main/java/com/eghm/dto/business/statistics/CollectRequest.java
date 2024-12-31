package com.eghm.dto.business.statistics;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DateComparator;
import com.eghm.enums.SelectType;
import com.eghm.enums.CollectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CollectRequest extends DateComparator {

    @ApiModelProperty(value = "开始日期 yyyy-MM-dd", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期 yyyy-MM-dd", required = true)
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty(value = "收藏类型(1:景区 2:民宿 3:零售门店 4:零售商品 5: 线路商品 6:餐饮门店 7:资讯 8:旅行社)", required = true)
    @NotNull(message = "收藏类型不能为空")
    private CollectType collectType;

    @ApiModelProperty(value = "查询类型")
    private SelectType selectType;
}
