package com.eghm.vo.operate.help;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@Data
public class HelpDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "帮助分类")
    private Integer helpType;

    @Schema(description = "问题标题")
    private String ask;

    @Schema(description = "状态 0:不显示 1:显示")
    private Integer state;

    @Schema(description = "回答(html)")
    private String answer;

}
