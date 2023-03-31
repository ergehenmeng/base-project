package com.eghm.dto.cache;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛 2022/6/25 16:19
 */
@Data
public class ClearCacheRequest {

    @ApiModelProperty(value = "缓存名称,逗号分割", required = true)
    @NotBlank(message = "请选择缓存项")
    private String cacheNames;
}
