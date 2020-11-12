package com.eghm.model.dto.help;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 殿小二
 * @date 2020/11/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpQueryDTO {

    /**
     * 帮助说明类型
     */
    @ApiModelProperty(value = "帮助说明类型:system_dict表中help_classify字段", required = true)
    private Byte classify;

    /**
     * 查询条件
     */
    @ApiModelProperty("关键字搜索(问或答)")
    private String queryName;
}
