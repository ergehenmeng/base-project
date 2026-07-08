package com.eghm.vo.operate.help;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class HelpResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "帮助分类取sys_dict表中help_type字段")
    private Integer helpType;

    @Schema(description = "状态 0:不显示 1:显示")
    private Integer state;

    @Schema(description = "问")
    private String ask;

    @Schema(description = "排序(小<->大)")
    private Integer sort;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
