package com.eghm.dto.operate.sensitvie;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/5/27
 */

@Data
public class KeywordDTO {

    @NotBlank(message = "敏感词不能为空")
    @ApiModelProperty("敏感词")
    private String keyword;
}
