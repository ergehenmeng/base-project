package com.eghm.application.shared.vo.operate.sensitive;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 敏感词响应
 *
 * @author 二哥很猛
 */
@Data
public class SensitiveWordResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "敏感字")
    private String keyword;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
