package com.eghm.dto.business.collect;

import com.eghm.enums.ref.CollectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
public class CollectDTO {

    @ApiModelProperty(value = "收藏id", required = true)
    @NotNull(message = "收藏id不能为空")
    private Long collectId;

    @ApiModelProperty(value = "收藏对象类型", required = true)
    @NotNull(message = "收藏对象类型不能为空")
    private CollectType collectType;
}
