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
public class CollectStatisticsVO {

    @ApiModelProperty(value = "收藏日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @ApiModelProperty(value = "收藏数量")
    private Integer collectNum = 0;

    public CollectStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
    }
}
