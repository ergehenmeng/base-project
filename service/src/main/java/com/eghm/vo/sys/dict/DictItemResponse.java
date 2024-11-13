package com.eghm.vo.sys.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/18
 */

@Data
public class DictItemResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("数据字典隐藏值")
    private Integer hiddenValue;

    @ApiModelProperty("显示值")
    private String showValue;
}
