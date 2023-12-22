package com.eghm.dto.poi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/21
 */
@Data
public class LinePointBindRequest {

    @ApiModelProperty(value = "线路", required = true)
    @NotNull(message = "线路id不能为空")
    private Long lineId;

    @ApiModelProperty(value = "点位列表")
    private List<Long> pointIds;
}
