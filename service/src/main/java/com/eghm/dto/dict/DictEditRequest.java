package com.eghm.dto.dict;

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
public class DictEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "数据字典名称", required = true)
    @NotBlank(message = "字典名称不能为空")
    @Size(min = 2, max = 10, message = "字典名称长度2~10位")
    private String title;

    @ApiModelProperty("字典分类: 1: 系统字典 2: 业务字典")
    @NotNull(message = "字典分类不能为空")
    private Integer dictType;

    @ApiModelProperty(value = "备注信息")
    @Size(max = 100, message = "备注信息不能超过100字")
    private String remark;
}
