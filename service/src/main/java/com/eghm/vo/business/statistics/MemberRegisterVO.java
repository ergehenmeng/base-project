package com.eghm.vo.business.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@NoArgsConstructor
public class MemberRegisterVO {

    @ApiModelProperty("注册日期")
    private LocalDate createDate;

    @ApiModelProperty("注册人数")
    private Integer registerNum = 0;

    public MemberRegisterVO(LocalDate createDate) {
        this.createDate = createDate;
    }
}
