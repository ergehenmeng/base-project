package com.eghm.vo.business.line;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "旅行社名称")
    private String title;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "是否收藏")
    private Boolean collect;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "旅行社描述信息")
    private String depict;

    @Schema(description = "旅行社图片")
    private String coverUrl;

    @Schema(description = "旅行社详细介绍信息")
    private String introduce;

    @Schema(description = "评分")
    private BigDecimal score;

}
