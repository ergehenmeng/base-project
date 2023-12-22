package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 线路点位关联表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("poi_line_point")
public class PoiLinePoint extends BaseEntity {

    @ApiModelProperty(value = "线路id")
    private Long lineId;

    @ApiModelProperty(value = "点位id")
    private Long pointId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
