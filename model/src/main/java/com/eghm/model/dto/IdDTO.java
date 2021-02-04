package com.eghm.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用于单一id对象映射
 * 禁止在该类中额外添加其他参数,除非是全局公用字段
 * @author 殿小二
 * @date 2020/8/29
 */
@Data
public class IdDTO {

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "参数不能为空")
    private Long id;
}
