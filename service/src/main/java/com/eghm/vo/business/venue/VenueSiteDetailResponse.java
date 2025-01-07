package com.eghm.vo.business.venue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSiteDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "场地封面图片")
    private String coverUrl;

    @Schema(description = "场地名称")
    private String title;

    @Schema(description = "场地价格介绍")
    private List<VenueSitePriceVO> priceList;
}
