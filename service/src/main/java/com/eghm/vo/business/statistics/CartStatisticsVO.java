package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartStatisticsVO {

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @ApiModelProperty(value = "月份")
    private String createMonth;

    @ApiModelProperty(value = "新增数量")
    private Integer cartNum;

    public CartStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
        this.cartNum = RandomUtil.randomInt(300);
    }

    public CartStatisticsVO(String createMonth) {
        this.createMonth = createMonth;
        this.cartNum = RandomUtil.randomInt(300);
    }
}
