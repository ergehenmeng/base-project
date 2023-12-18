package com.eghm.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:50
 */
@Data
public class DictItemEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "数据字典隐藏值", required = true)
    @NotBlank(message = "隐藏值不能为空")
    private Integer hiddenValue;

    @ApiModelProperty(value = "显示值", required = true)
    @NotBlank(message = "显示值不能为空")
    private String showValue;
}
