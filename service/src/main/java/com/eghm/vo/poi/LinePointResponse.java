package com.eghm.vo.poi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/22
 */
@Data
public class LinePointResponse {

    @ApiModelProperty("选中的点位信息")
    private List<Long> checkedList;

    @ApiModelProperty("所有点位信息")
    private List<BasePointResponse> pointList;
}
