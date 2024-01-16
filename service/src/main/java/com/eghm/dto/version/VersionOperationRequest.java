package com.eghm.dto.version;

import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @date 2021/10/27
 */
@Data
public class VersionOperationRequest {

    public static final int STATE_1 = 1;

    public static final int STATE_2 = 2;

    public static final int STATE_3 = 3;

    @ApiModelProperty(required = true, value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(required = true, value = "id")
    @OptionInt(value = {STATE_1, STATE_2, STATE_3}, message = "操作状态非法")
    private Integer state;
}
