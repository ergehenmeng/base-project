package com.eghm.vo.sys.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/11
 */

@Data
public class BaseItemVO {

    @ApiModelProperty("数据字典隐藏值")
    private Integer hiddenValue;

    @ApiModelProperty("显示值")
    private String showValue;
}
