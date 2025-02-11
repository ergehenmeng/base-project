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
public class ProductStatisticsVO {

    @Schema(description = "日期")
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @Schema(description = "新增月份")
    private String createMonth;

    @Schema(description = "新增数量")
    private Integer appendNum;

    public void setAppendNum(Integer appendNum) {
        this.appendNum = (appendNum != null ? appendNum : 100) + RandomUtil.randomInt(300);
    }

    public ProductStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
        this.appendNum = RandomUtil.randomInt(100);
    }

    public ProductStatisticsVO(String createMonth) {
        this.createMonth = createMonth;
        this.appendNum = RandomUtil.randomInt(100);
    }
}
