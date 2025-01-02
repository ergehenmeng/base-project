package com.eghm.vo.operate.help;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@Data
public class HelpCenterVO {

    @Schema(description = "帮助分类")
    private Integer helpType;

    @Schema(description = "问题标题")
    private String ask;

    @Schema(description = "回答")
    private String answer;
}
