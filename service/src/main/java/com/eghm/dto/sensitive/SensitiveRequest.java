package com.eghm.dto.sensitive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/4/2
 */
@Data
public class SensitiveRequest {

    @ApiModelProperty("敏感词")
    @NotBlank(message = "请选择要删除的敏感词")
    private String keyword;
}
