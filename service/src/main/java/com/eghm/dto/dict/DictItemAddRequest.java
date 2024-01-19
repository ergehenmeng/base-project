package com.eghm.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:40
 */
@Data
public class DictItemAddRequest {

    @ApiModelProperty(value = "字典编码", required = true)
    @NotBlank(message = "字典编码不能为空")
    private String nid;

    @ApiModelProperty(value = "数据字典隐藏值", required = true)
    @NotBlank(message = "隐藏值不能为空")
    private Integer hiddenValue;

    @ApiModelProperty(value = "显示值", required = true)
    @NotBlank(message = "显示值不能为空")
    private String showValue;
}
