package com.eghm.vo.sys.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/11
 */

@Data
public class BaseItemVO {

    @Schema(description = "数据字典隐藏值")
    private Integer hiddenValue;

    @Schema(description = "显示值")
    private String showValue;
}
