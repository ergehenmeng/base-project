package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "线路id")
    private Long lineId;

    @Schema(description = "点位id")
    private Long pointId;

    @Schema(description = "排序")
    private Integer sort;

}
