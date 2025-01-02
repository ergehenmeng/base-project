package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.eghm.enums.Channel;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberChannelVO {

    @Schema(description = "注册渠道")
    private Channel name;

    @Schema(description = "人数")
    private Integer value = 0;

    public MemberChannelVO(Channel name) {
        this.name = name;
        this.value = RandomUtil.randomInt(500);
    }
}
