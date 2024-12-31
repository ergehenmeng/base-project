package com.eghm.dto.operate.help;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpQueryDTO {

    @ApiModelProperty(value = "帮助说明类型:system_dict表中help_type字段", required = true)
    @NotNull(message = "帮助分类不能为空")
    private Integer helpType;

    @ApiModelProperty("关键字搜索(问或答)")
    private String queryName;
}
