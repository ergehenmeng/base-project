package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * poi类型表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("poi_type")
public class PoiType extends BaseEntity {

    @Schema(description = "poi类型名称")
    private String title;

    @Schema(description = "区域编号")
    private String areaCode;

    @Schema(description = "点位类型icon")
    private String icon;

    @Schema(description = "点位排序 小<->大")
    private Integer sort;

}
