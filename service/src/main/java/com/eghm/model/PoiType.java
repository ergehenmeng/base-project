package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "poi类型名称")
    private String title;

    @ApiModelProperty(value = "区域编号")
    private String areaCode;

    @ApiModelProperty(value = "点位类型icon")
    private String icon;

    @ApiModelProperty(value = "点位排序 小<->大")
    private Integer sort;

}
