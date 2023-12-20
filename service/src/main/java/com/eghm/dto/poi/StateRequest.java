package com.eghm.dto.poi;

import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class StateRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "状态", required = true)
    @OptionInt(value = {0, 1}, message = "状态不能为空")
    private Integer state;
}
