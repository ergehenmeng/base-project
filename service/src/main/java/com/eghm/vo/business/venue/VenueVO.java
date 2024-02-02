package com.eghm.vo.business.venue;

import com.eghm.enums.ref.VenueType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "场馆名称")
    private String title;

    @ApiModelProperty(value = "场馆类型")
    private VenueType venueType;

    @ApiModelProperty(value = "场馆封面图")
    private String coverUrl;

    @ApiModelProperty("距离 单位:m")
    private Integer distance;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

}
