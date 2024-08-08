package com.eghm.vo.business.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class MemberVisitVO {

    @ApiModelProperty("访问日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @ApiModelProperty("订单数量")
    private Integer visitNum = 0;

    public MemberVisitVO(LocalDate createDate) {
        this.createDate = createDate;
    }
}
