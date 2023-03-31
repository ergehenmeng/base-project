package com.eghm.model.dto.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 更新系统配置信息的请求参数类
 * @author 二哥很猛
 * @date 2018/1/12 17:37
 */
@Data
public class ConfigEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "参数名称", required = true)
    @NotBlank(message = "参数名称不能为空")
    private String title;

    @ApiModelProperty(value = "参数类型(sys_dict#config_classify所配置)", required = true)
    @NotNull(message = "参数类型不能为空")
    private Integer classify;

    @ApiModelProperty(value = "参数值", required = true)
    @NotBlank(message = "参数值不能为空")
    private String content;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "锁定状态 false未锁定 true锁定无法编辑")
    private Boolean locked;

}
