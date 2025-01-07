package com.eghm.vo.business.order.line;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class LineOrderSnapshotVO {

    @Schema(description = "行程排序(第几天)")
    private Integer routeIndex;

    @Schema(description = "出发地点")
    private String startPoint;

    @Schema(description = "结束地点")
    private String endPoint;

    @Schema(description = "交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他")
    private Integer trafficType;

    @Schema(description = "包含就餐 1:早餐 2:午餐 4:晚餐")
    private Integer repast;

    @Schema(description = "详细描述信息")
    private String depict;
}
