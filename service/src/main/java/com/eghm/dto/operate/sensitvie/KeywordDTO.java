package com.eghm.dto.operate.sensitvie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/5/27
 */

@Data
public class KeywordDTO {

    @NotBlank(message = "敏感词不能为空")
    @Schema(description = "敏感词")
    private String keyword;
}
