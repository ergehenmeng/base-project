package com.eghm.vo.business.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVisitVO {

    @ApiModelProperty("访问日期")
    private LocalDate createDate;

    @ApiModelProperty("订单数量")
    private Integer visitNum;

    public MemberVisitVO(LocalDate createDate) {
        this.createDate = createDate;
    }
}
