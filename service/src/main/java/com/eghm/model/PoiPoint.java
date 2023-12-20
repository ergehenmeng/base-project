package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * poi点位信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("poi_point")
public class PoiPoint extends BaseEntity {

    @ApiModelProperty(value = "点位名称")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "所属类型")
    private Long typeId;

    @ApiModelProperty(value = "区域编号(冗余)")
    private String areaCode;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

}
