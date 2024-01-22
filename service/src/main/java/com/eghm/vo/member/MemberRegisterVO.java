package com.eghm.vo.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterVO {

    @ApiModelProperty("注册日期")
    private LocalDate createDate;

    @ApiModelProperty("注册人数")
    private Integer registerNum;
}
