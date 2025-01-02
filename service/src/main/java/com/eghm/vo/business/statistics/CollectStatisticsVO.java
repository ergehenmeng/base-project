package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CollectStatisticsVO {

    @Schema(description = "收藏日期")
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @Schema(description = "收藏月份")
    private String createMonth;

    @Schema(description = "收藏数量")
    private Integer collectNum = 0;

    public CollectStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
        this.collectNum = RandomUtil.randomInt(200);
    }

    public CollectStatisticsVO(String createMonth) {
        this.createMonth = createMonth;
        this.collectNum = RandomUtil.randomInt(200);
    }
}
