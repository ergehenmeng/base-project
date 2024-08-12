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
 * @since 2024/1/23
 */

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberVisitVO {

    @ApiModelProperty("访问日期")
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @ApiModelProperty("访问月份")
    private String createMonth;

    @ApiModelProperty("订单数量")
    private Integer visitNum = 0;

    public MemberVisitVO(LocalDate createDate) {
        this.createDate = createDate;
        this.visitNum = RandomUtil.randomInt(100);
    }

    public MemberVisitVO(String createMonth) {
        this.createMonth = createMonth;
        this.visitNum = RandomUtil.randomInt(100);
    }
}
