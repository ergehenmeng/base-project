package com.eghm.vo.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/22
 */
@Data
public class LinePointResponse {

    @Schema(description = "选中的点位信息")
    private List<Long> checkedList;

    @Schema(description = "所有点位信息")
    private List<BasePointResponse> pointList;
}
