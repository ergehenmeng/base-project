package com.eghm.application.shared.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
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
public class PieDataVO {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "值")
    private Integer value;

    public PieDataVO(String name) {
        this.name = name;
        this.value = RandomUtil.randomInt(500);
    }
}
