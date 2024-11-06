package com.eghm.dto.sys.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 更新系统配置信息的请求参数类
 *
 * @author 二哥很猛
 * @since 2018/1/12 17:37
 */
@Data
public class ConfigEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "参数名称", required = true)
    @NotBlank(message = "参数名称不能为空")
    private String title;

    @ApiModelProperty(value = "参数值", required = true)
    @NotBlank(message = "参数值不能为空")
    private String content;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
