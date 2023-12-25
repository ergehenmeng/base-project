package com.eghm.vo.poi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class PoiPointVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "点位名称")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "介绍")
    private String introduce;
}
