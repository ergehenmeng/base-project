package com.eghm.dto.sys.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:50
 */
@Data
public class DictItemEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "字典编码", required = true)
    @NotBlank(message = "字典编码不能为空")
    private String nid;

    @ApiModelProperty(value = "数据字典隐藏值", required = true)
    @NotNull(message = "隐藏值不能为空")
    private Integer hiddenValue;

    @ApiModelProperty(value = "显示值", required = true)
    @NotBlank(message = "显示值不能为空")
    @Size(min = 1, max = 20, message = "显示值长度1~20位")
    private String showValue;
}
