package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberSexVO {

    @Schema(description = "注册渠道")
    private String name;

    @Schema(description = "人数")
    private Integer value;

    public MemberSexVO() {
        this.value = RandomUtil.randomInt(500);
    }
}
