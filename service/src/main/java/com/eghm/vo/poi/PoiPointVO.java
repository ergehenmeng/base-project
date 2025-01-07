package com.eghm.vo.poi;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "点位名称")
    private String title;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "维度")
    private BigDecimal latitude;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "介绍")
    private String introduce;
}
