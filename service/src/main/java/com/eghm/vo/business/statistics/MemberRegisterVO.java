package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @ApiModelProperty("注册人数")
    private Integer registerNum = 0;

    public MemberRegisterVO(LocalDate createDate) {
        this.createDate = createDate;
        this.registerNum = RandomUtil.randomInt(200);
    }
}
