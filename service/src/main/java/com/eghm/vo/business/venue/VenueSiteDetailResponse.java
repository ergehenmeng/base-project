package com.eghm.vo.business.venue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSiteDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "场地封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "场地名称")
    private String title;

    @ApiModelProperty("场地价格介绍")
    private List<VenueSitePriceVO> priceList;
}
