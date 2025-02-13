package com.eghm.dto.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/21
 */
@Data
public class LinePointBindRequest {

    @Schema(description = "线路", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "线路id不能为空")
    private Long lineId;

    @Schema(description = "点位列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择点位")
    @Size(min = 2, message = "最少绑定两个点位")
    private List<Long> pointIds;
}
