package com.eghm.vo.business.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CartStatisticsVO {

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @ApiModelProperty(value = "新增数量")
    private Integer cartNum = 0;

    public CartStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
    }
}
