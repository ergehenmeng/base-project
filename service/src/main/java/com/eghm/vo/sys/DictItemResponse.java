package com.eghm.vo.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/18
 */

@Data
public class DictItemResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "数据字典隐藏值")
    private Integer hiddenValue;

    @Schema(description = "显示值")
    private String showValue;
}
