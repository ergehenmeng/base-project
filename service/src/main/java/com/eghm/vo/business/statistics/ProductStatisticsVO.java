package com.eghm.vo.business.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
@NoArgsConstructor
public class ProductStatisticsVO {

    @ApiModelProperty(value = "日期")
    private LocalDate createDate;

    @ApiModelProperty(value = "新增数量")
    private Integer appendNum = 0;

    public ProductStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
    }
}
