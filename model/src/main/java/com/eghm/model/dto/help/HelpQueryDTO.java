package com.eghm.model.dto.help;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @date 2020/11/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpQueryDTO {

    @ApiModelProperty(value = "帮助说明类型:system_dict表中help_classify字段", required = true)
    @NotNull(message = "帮助分类不能为空")
    private Integer classify;

    @ApiModelProperty("关键字搜索(问或答)")
    private String queryName;
}
