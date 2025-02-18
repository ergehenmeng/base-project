package com.eghm.vo.business.line;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 旅行社信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-02-18
 */
@Data
public class TravelDetailVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "旅行社名称")
    private String title;

    @ApiModelProperty("店铺logo")
    private String logoUrl;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty("是否收藏")
    private Boolean collect;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "旅行社描述信息")
    private String depict;

    @ApiModelProperty(value = "旅行社图片")
    private String coverUrl;

    @ApiModelProperty(value = "旅行社详细介绍信息")
    private String introduce;

    @ApiModelProperty("评分")
    private BigDecimal score;

    @ApiModelProperty(value = "距离 单位:m")
    private Integer distance;
}
