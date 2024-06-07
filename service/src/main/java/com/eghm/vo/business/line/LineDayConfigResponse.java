package com.eghm.vo.business.line;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/12/26
 */
@Data
public class LineDayConfigResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "行程排序(第几天)")
    private Integer routeIndex;

    @ApiModelProperty(value = "出发地点")
    private String startPoint;

    @ApiModelProperty(value = "结束地点")
    private String endPoint;

    @ApiModelProperty(value = "交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他")
    private Integer trafficType;

    @ApiModelProperty(value = "包含就餐 1:早餐 2:午餐 4:晚餐")
    private List<Integer> repastList;

    @ApiModelProperty(value = "详细描述信息")
    private String depict;
}
