package com.eghm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用于单一ids对象映射
 *
 * @author 殿小二
 * @since 2024/4/1
 */
@Data
public class IdsDTO {

    @ApiModelProperty(value = "id", required = true)
    @NotEmpty(message = "id不能为空")
    private List<Long> ids;
}
