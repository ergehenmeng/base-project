package com.eghm.dto.poi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @ApiModelProperty(value = "点位列表", required = true)
    @NotNull(message = "请选择点位")
    @Size(min = 2, message = "最少绑定两个点位")
    private List<Long> pointIds;
}
