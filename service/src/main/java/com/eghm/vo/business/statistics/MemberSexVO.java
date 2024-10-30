package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberSexVO {

    @ApiModelProperty("注册渠道")
    private String name;

    @ApiModelProperty("人数")
    private Integer value = 0;

    public MemberSexVO() {
        this.value = RandomUtil.randomInt(500);
    }
}
